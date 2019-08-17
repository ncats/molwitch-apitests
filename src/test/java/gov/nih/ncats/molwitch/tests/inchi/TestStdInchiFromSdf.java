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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.inchi.InChiResult;
import gov.nih.ncats.molwitch.inchi.Inchi;
import gov.nih.ncats.molwitch.io.ChemicalReader;
import gov.nih.ncats.molwitch.io.ChemicalReaderFactory;
import gov.nih.ncats.molwitch.internal.InternalUtil;
@RunWith(Parameterized.class)
//@Ignore
public class TestStdInchiFromSdf {

	private InChiResult result;
	private String expectedKey;
	private String expectedInchi;
	private String id;
	
	@Parameters(name = "{3}")
	public static List<Object[]> data() throws IOException{
		List<Object[]> list = new ArrayList<>();
		try(//InputStream in = TestStdInchiFromSdf.class.getResourceAsStream("/molFiles/withProperties.sdf");
				InputStream in = new GZIPInputStream(TestStdInchiFromSdf.class.getResourceAsStream("/inChis/chembl_24_1_with_1000_Inchis.sdf.gz"));
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
	public TestStdInchiFromSdf(String expectedInchi, String key, Chemical chem, String id) throws IOException {
		this.expectedInchi = expectedInchi;
		this.expectedKey = key;
		if(id.equals("CHEMBL439138")) {
			InternalUtil.on();
		}
		this.result = Inchi.asStdInchi(chem, true);
		if(id.equals("CHEMBL439138")) {
			InternalUtil.off();
		}
		if(this.result.getInchi() ==null){
			throw new IllegalStateException(result.toString());
		}
		this.id = id;
	}


	@Test
	public void mol2Inchi(){
	
		assertEquals(id + " : " + result.getMessage(), expectedInchi, removeInChiPrefix(result.getInchi()));
		
	}
	
	@Test
	public void key(){
		assertEquals(id, expectedKey, result.getKey());
	}
}
