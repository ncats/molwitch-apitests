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

package gov.nih.ncats.molwitch.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.ChemicalSource;

public class UnknownChemicalStringInputTestApi {
    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("mol parser unknown format");

    @Test
	public void smiles() throws IOException{
		Chemical chemical = Chemical.parse("COC1=CC=C(O)C2=C(O)C(C)=C3OC(C)(O)C(=O)C3=C12");
		assertEquals(ChemicalSource.Type.SMILES, chemical.getSource().get().getType());
	}
	
	@Test
	public void smarts() throws IOException{
		Chemical chemical = Chemical.parse("[#7,#8]~C1=c2c3c(OC([#6])(O)C3=O)cc(O)c2=C(O)\\C=C/1");
		ChemicalSource.Type type = chemical.getSource().get().getType();
		//some implementation might consider this smarts or marvin/jchem extended smiles cxsmiles
		assertTrue(type.toString(), type == ChemicalSource.Type.SMARTS|| type == ChemicalSource.Type.SMILES);
	}
	@Test
    public void complexSmarts() throws IOException{
	    Chemical chemical = Chemical.parse("[#6]=,:[#6]C(CCCC([He])C(~[#8])c1cc([He])cc(c1)[#6]([#6])[He])CC=C");
        ChemicalSource.Type type = chemical.getSource().get().getType();
        //some implementation might consider this smarts or marvin/jchem extended smiles cxsmiles
        assertTrue(type.toString(), type == ChemicalSource.Type.SMARTS|| type == ChemicalSource.Type.SMILES);
    }
	
	@Test
	public void mol() throws IOException{
		String mol = convertStreamToString(this.getClass().getResourceAsStream("/molFiles/simple.mol"));
		Chemical chemical = Chemical.parse(mol);
		Chemical expected = Chemical.parseMol(mol);
		
		assertEquals(expected.getAtomCount(), chemical.getAtomCount());
		assertEquals(expected.getBondCount(), chemical.getBondCount());
		
	}
	
	private static String convertStreamToString(InputStream is) {
	    try(Scanner s = new Scanner(is).useDelimiter("\\A")){
	    	return s.hasNext() ? s.next() : "";
	    }
	}
}
