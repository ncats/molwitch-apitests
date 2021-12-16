/*
 * NCATS-WITCH-APITESTS
 *
 * Copyright 2019 NIH/NCATS
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package gov.nih.ncats.molwitch.tests.inchi;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import gov.nih.ncats.molwitch.tests.contract.ApiContractChecker;
import gov.nih.ncats.molwitch.tests.contract.PercentageApiContractChecker;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.inchi.InChiResult;
import gov.nih.ncats.molwitch.inchi.Inchi;
import gov.nih.ncats.molwitch.io.ChemicalReader;
import gov.nih.ncats.molwitch.io.ChemicalReaderFactory;

@RunWith(Parameterized.class)
//@Ignore
public class StdInchiFromSdfTestApi {

	private InChiResult result;
	private String expectedKey;
	private String expectedInchi;
	private String id;

	@ClassRule
	public static PercentageApiContractChecker apiContractChecker = new PercentageApiContractChecker(map->{
	    //there are 1000 records let's make it fail for > 1%
        return map.entrySet().stream()
                    .filter(e-> e.getValue() < 0.990D)
                    .findAny()
                    .isPresent();

    }){
	};

	@Parameters(name = "{3}")
	public static List<Object[]> data() throws IOException{
		List<Object[]> list = new ArrayList<>();
		try(//InputStream in = TestStdInchiFromSdf.class.getResourceAsStream("/molFiles/withProperties.sdf");
            InputStream in = new GZIPInputStream(StdInchiFromSdfTestApi.class.getResourceAsStream("/inChis/chembl_24_1_with_1000_Inchis.sdf.gz"));
            ChemicalReader reader = ChemicalReaderFactory.newReader(in);
			){
//			System.out.println("here");
//			for(int i=0; i< 500; i++){
			while(reader.canRead()){
				Chemical chem = reader.read();
//				String smiles = chem.getProperty("PUBCHEM_OPENEYE_ISO_SMILES", true);
				String chembl_id = chem.getProperty("chembl_id").trim();
				
				String inchi = chem.getProperty("Inchi", true);
				String key = chem.getProperty("InChIKey", true);
				list.add( new Object[]{removeInChiPrefix(inchi), key, chem, chembl_id});
			}
		}
		return list;
	}
	private static String removeInChiPrefix(String inchi){
		//substring 6 because all start "InChi="
		return inchi.substring(6);
	}
	public StdInchiFromSdfTestApi(String expectedInchi, String key, Chemical chem, String id) throws IOException {
		this.expectedInchi = expectedInchi;
		this.expectedKey = key;

		this.result = Inchi.asStdInchi(chem, true);

		if(this.result.getInchi() ==null){
			throw new IllegalStateException(result.toString());
		}
		this.id = id;
	}



	@Test
	public void mol2Inchi(){
	    String actual = removeInChiPrefix(result.getInchi());

		boolean equals = expectedInchi.equals(actual);
		if(!equals){
			System.err.println("["+id + "]: full inchi wrong. : expected '"+expectedInchi +"' actual :'"+actual+"'");
		}
		apiContractChecker.addComplianceReport("fullInchi",
                equals ? ApiContractChecker.ComplianceLevel.FULLY: ApiContractChecker.ComplianceLevel.NOT_COMPLIANT );
		
	}
	
	@Test
	public void key(){
		boolean equals = expectedKey.equals(result.getKey());
		if(!equals){
			System.err.println("["+id + "]: inchiKey wrong. : expected '"+expectedKey +"' actual :'"+result.getKey()+"'");
		}
        apiContractChecker.addComplianceReport("inchiKey",
				equals ? ApiContractChecker.ComplianceLevel.FULLY: ApiContractChecker.ComplianceLevel.NOT_COMPLIANT);
	}
}
