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

import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import gov.nih.ncats.witch.Atom;
import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.inchi.Inchi;

import static org.junit.Assert.*;
public class TestInchiParser {

	@Test
	public void parse() throws IOException{
		String inChi = "InChI=1S/C17H12ClN3O3/c1-10-8-11(21-17(24)20-15(22)9-19-21)6-7-12(10)16(23)13-4-2-3-5-14(13)18/h2-9H,1H3,(H,20,22,24)";
		
		Chemical chem = Inchi.toChemical(inChi);
		
		
		assertEquals(24, chem.getAtomCount());
		assertEquals(17, getNumberOfCarbons(chem));
	}
	
	
	@Test
	public void toInChi() throws IOException{
		String smiles = "Cc1cc(ccc1C(=O)c2ccccc2Cl)N3N=CC(=O)NC3=O";
		String expected = "InChI=1S/C17H12ClN3O3/c1-10-8-11(21-17(24)20-15(22)9-19-21)6-7-12(10)16(23)13-4-2-3-5-14(13)18/h2-9H,1H3,(H,20,22,24)";
		
		assertEquals(expected, Inchi.asStdInchi(Chemical.createFromSmilesAndComputeCoordinates(smiles))
									.getInchi());
	}

	private int getNumberOfCarbons(Chemical chem) {
		
		return (int) chem.atoms()
						.filter(a-> a.getAtomicNumber() == 6)
						.count();
		
//		Iterator<Atom> iter = chem.getAtomIterator();
//		int count=0;
//		while(iter.hasNext()){
//			Atom a = iter.next();
//			if(a.getAtomicNumber() == 6){
//				count++;
//			}
//		}
//		return count;
	}
}
