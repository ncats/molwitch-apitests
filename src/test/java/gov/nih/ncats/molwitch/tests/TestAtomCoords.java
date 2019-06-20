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

import org.junit.Test;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.AtomCoordinates;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.io.ChemicalReader;
import gov.nih.ncats.molwitch.io.ChemicalReaderFactory;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class TestAtomCoords {

	@Test
	public void unsetCoordsShouldDefaultToZero() {
		Chemical chem = new Chemical();
		Atom atom = chem.addAtom("C");

		Atom atom2 = chem.addAtom("C");
		assertNull(atom.getAtomCoordinates());
	}

	@Test
	public void setCoords() {
		Chemical chem = new Chemical();
		Atom atom = chem.addAtom("C");

		atom.setAtomCoordinates(AtomCoordinates.valueOf(1, 2, 3));

		assertEquals(AtomCoordinates.valueOf(1, 2, 3), atom.getAtomCoordinates());
	}

	@Test
	public void molFileWithAllZeroCoords() throws IOException {
		// have to use inputstream because the submodules
		// only access the mol file from inside the jar
		// so can only access it as a resource stream not a File obj.
		byte[] bytes;
		try (InputStream in = getClass().getResourceAsStream("/molFiles/simple.mol")) {
			bytes = TestUtil.toByteArray(in);
		}
		try (ChemicalReader reader = ChemicalReaderFactory.newReader(bytes)) {
			Chemical chem = reader.read();
			assertEquals(16, chem.getAtomCount());
			AtomCoordinates zeroedCoords = AtomCoordinates.valueOf(0,0,0);
			for (int i = 0; i < chem.getAtomCount(); i++) {
				Atom atom = chem.getAtom(i);

				assertNull( atom.getAtomCoordinates());
			}
		}
	}

	@Test
	public void molFileWith2DCoords() throws IOException {
		// have to use inputstream because the submodules
		// only access the mol file from inside the jar
		// so can only access it as a resource stream not a File obj.
		byte[] bytes;
		try (InputStream in = getClass().getResourceAsStream("/molFiles/entecavir.mol")) {
			bytes = TestUtil.toByteArray(in);
		}
		try (ChemicalReader reader = ChemicalReaderFactory.newReader(bytes)) {
			Chemical chem = reader.read();

			assertEquals(20, chem.getAtomCount());
			// each submodules does different scaling and translations
			// to the coordinates so we can't just check that the values
			// match the mol file

			// so just assume the submodules are handling coordinates
			// correctly and make sure we get something back
			Optional<Atom> atomWithNonZeroCoords = chem.atoms().filter(a ->{
				AtomCoordinates coords = a.getAtomCoordinates();
				return coords !=null && coords.getX() !=0D && coords.getY() !=0D;
			}).findAny();
			

			assertTrue(atomWithNonZeroCoords.isPresent());


		}
	}

	
}
