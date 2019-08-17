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

import org.junit.Test;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Chemical;

import static org.junit.Assert.*;

public class TestChemicalClone {

	@Test
	public void cloneShouldHaveSameSmiles() throws IOException{
		Chemical chem = createFromSmiles("c1ccccc1");
		
		Chemical sut = chem.copy();
		
		Atom atom = chem.getAtom(0);
		
		assertNotSame(atom, sut.getAtom(0));
		assertEquals(chem.toSmiles(), sut.toSmiles());
	}
	
	private Chemical createFromSmiles(String smiles) throws IOException{
		return Chemical.createFromSmilesAndComputeCoordinates(smiles);
		
	}
}
