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

package gov.nih.ncats.witch.tests.inchi;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gov.nih.ncats.witch.ChemicalBuilder;
import gov.nih.ncats.witch.inchi.InChiResult;
import gov.nih.ncats.witch.inchi.Inchi;
@RunWith(Parameterized.class)
@Ignore("some tests still fail")
public class TestStdInChiKeys {

	private String inchi;
	private String key;
	private InChiResult result;


	private static Set<String> keysToSkip(){
		Set<String> set = new HashSet<>();
		set.add("InChIKey=QFSGJJCWIIUQCT-UHFFFAOYSA-P");
		set.add("InChIKey=XEMPJHUQLPZELF-UHFFFAOYSA-P");
		set.add("InChIKey=PWKOKPCVDQPYGH-UHFFFAOYSA-P");
		set.add("InChIKey=SXDHPHPRCINSFL-UHFFFAOYSA-P");
		set.add("InChIKey=XTPOJZQDRYWGAK-UHFFFAOYSA-P");

		set.add("InChIKey=NVFMGTWRSCHMNV-UHFFFAOYSA-P");
		
		set.add("InChIKey=HZWIRYNNPCVNQT-QURGRASLSA-N");
		set.add("InChIKey=QTBBQYWWGOKLHG-DQYVZNEBSA-N");
		set.add("InChIKey=QJJXYPPXXYFBGM-PBWQTLGUSA-N");
		
		
		//CDK only
		set.add("InChIKey=QFJCIRLUMZQUOT-HPLJOQBZSA-N");
		set.add("InChIKey=LYTCVQQGCSNFJU-LKGYBJPKSA-N");
		return set;
	}

	@Parameters(name = "{1}")
	public static List<Object[]> getInchis() throws IOException{
		List<Object[]> list = new ArrayList<>(2000);
		Pattern WHITE_SPACE = Pattern.compile("\\s+");

		Set<String> toSkip = keysToSkip();
		//TODO fix more
		int count=1000;
		try(InputStream in = TestStdInChiKeys.class.getResourceAsStream("/inChis/with_stdinchikey.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(in)));
		){
			String line;
			int i=0;
			while( i<count && (line = reader.readLine()) !=null){
				i++;
				String[] fields = WHITE_SPACE.split(line);
				String inchiKey = fields[3];
				if(toSkip.contains(inchiKey)){
					continue;
				}
				list.add(new Object[]{fields[0], fields[2], fields[3]});

			}
		}
		
		
		return list;
	}
	
	public TestStdInChiKeys(String inchi, String smiles, String inchiKey) throws IOException {
		this.inchi = inchi;
		this.key = inchiKey;

//		if(inchiKey.contains("HZWIRYNNPCVNQT-QURGRASLSA-N")){
//			System.out.println("here");
//		}
		//             C1CCCCCNc2cc[n+](Cc3cccc(c3)c4cccc(C[n+]5ccc(NCCCC1)c6ccccc56)c4)c7ccccc27
//		if(smiles.contains("c4)c7ccccc27")){
//			System.out.println("here : " + key);
//			InternalUtil.printDebug(()->{
//				result = InChiUtil.toStdInchi(ChemicalBuilder.createFromSmiles(smiles)
//						.computeCoordinates(true)
//						.build());
//			});
//		}else {
			result = Inchi.asStdInchi(ChemicalBuilder.createFromSmiles(smiles)
					.computeCoordinates(true)
					.computeStereo(true)
					.build(),
					true);
		//}
	}
	
	@Test
	public void inchi() throws IOException{

		String message = result.getMessage();
		if(message ==null){
			assertEquals(inchi, result.getInchi());
		}else{
			assertEquals( inchi, result.getInchi());
		}
	}
	@Test
	public void key() throws IOException{
		
		assertEquals("expected " +inchi + " but was " + result.getInchi(), key, "InChIKey="+result.getKey());
	}
	
	
}
