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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

import gov.nih.ncats.witch.Witch;
import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.inchi.InChiResult;
import gov.nih.ncats.witch.inchi.Inchi;

@Ignore
public class TestBrokenInchi {

	@Test
	public void getInchiResults() throws IOException{
		String moduleName = Witch.getModuleName();
		try(BufferedReader in = new BufferedReader(new FileReader("/home/katzelda/Downloads/Failed_InChis_example.txt"));
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("badInchis."+ moduleName + "_fixed.txt")))
				){
			writer.println("smiles\texpected key\t"+moduleName+" key\texpected inchi\t"+moduleName +" inchi\t warnings");
			String line = in.readLine(); //skip header
			while( (line = in.readLine()) !=null){
				String[] fields = line.split("\t");
				System.out.println(Arrays.toString(fields));
				String inchi = fields[0];
				String smiles = fields[2];
				String inchiKey = fields[3].substring(9);
				
				getInchifor(writer, inchi, smiles, inchiKey);
				
			}
			
			getInchifor(writer, "", "C1CCCCCNc2cc[n+](Cc3ccc(cc3)c4ccc(C[n+]5ccc(NCCCC1)c6ccccc56)cc4)c7ccccc27", "NVFMGTWRSCHMNV-UHFFFAOYSA-P");
			getInchifor(writer, "", "CCCN(NC(=O)[C@H]1CCCN(NC(=O)[C@H]1CCCN1C(=O)[C@@H](NC(=O)[C@@H](NC(=O)[C@H](CC(=O)O)NC(=O)[C@H](CCC(=O)O)NC(=O)[C@@H](NC(=O)[C@H](CC(=O)O)NC(=O)C)[C@@H](C)O)C(C)C)C(C)C)C(=O)c2ccc(cc2)N=Nc3ccc(cc3)[N+](=O)[O]CCCN1C(=O)[C@@H](NC(=O)[C@@H](NC(=O)[C@H](CC(=O)O)NC(=O)[C@H](CCC(=O)O)NC(=O)[C@@H](NC(=O)[C@H](CC(=O)O)NC(=O)C)[C@@H](C)O)C(C)C)C(C)C)C(=O)c2ccc(cc2)N=Nc3ccc(cc3)[N+](=O)[O-]",  "");
		}
	}

	private void getInchifor(PrintWriter writer, String inchi, String smiles, String inchiKey) throws IOException {
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		
		InChiResult result = Inchi.asStdInchi(chem);
		
		List<String> values = Arrays.asList(smiles, inchiKey, result.getKey(),
				inchi, result.getInchi(),
				result.getMessage()
				);
		writer.println(values.stream().collect(Collectors.joining("\t")));
	}
}
