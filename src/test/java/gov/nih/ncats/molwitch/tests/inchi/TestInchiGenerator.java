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

import java.io.IOException;

import static org.junit.Assert.*;

import gov.nih.ncats.molwitch.ChemkitException;
import org.junit.Test;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.ChemicalBuilder;
import gov.nih.ncats.molwitch.Bond.BondType;
import gov.nih.ncats.molwitch.inchi.InChiResult;
import gov.nih.ncats.molwitch.inchi.Inchi;

public class TestInchiGenerator {

	// many of these tests taken from CDK's InChiGeneratorTest
	@Test
	public void testGetInchiFromLithiumIon() throws Exception {
		ChemicalBuilder chem = new ChemicalBuilder();
		Atom atom = chem.addAtom("Li");
		atom.setCharge(1);
		InChiResult result = Inchi.asStdInchi(chem.build());

		assertEquals("InChI=1S/Li/q+1", result.getInchi());
	}
	
	@Test
    public void testIsotope() throws Exception {
		ChemicalBuilder chem = new ChemicalBuilder();
		Atom atom = chem.addAtom("Cl");
        atom.setMassNumber(37);
       
        InChiResult result = Inchi.asStdInchi(chem.build());

        assertEquals("InChI=1S/ClH/h1H/i1+2", result.getInchi());
    }
	
	@Test
	public void ethane() throws IOException{
		ChemicalBuilder chem = new ChemicalBuilder();
		Atom a1 = chem.addAtom("C");
		Atom a2 = chem.addAtom("C");
		
		chem.addBond(a1, a2, BondType.SINGLE);
		InChiResult result = Inchi.asStdInchi(chem.build());

		 assertEquals("InChI=1S/C2H6/c1-2/h1-2H3", result.getInchi());
	     assertEquals("OTMSDBZUPAUEDD-UHFFFAOYSA-N", result.getKey());
	}
	
	@Test
	public void ethene() throws IOException{
		ChemicalBuilder chem = new ChemicalBuilder();
		Atom a1 = chem.addAtom("C");
		Atom a2 = chem.addAtom("C");
		
		
		chem.addBond(a1, a2, BondType.DOUBLE);
		
		
		Chemical mol = chem.build();

//		assertEquals(2, mol.getAtom(0).getImplicitHCount());
		
		InChiResult result = Inchi.asStdInchi(mol);

		 assertEquals("InChI=1S/C2H4/c1-2/h1-2H2", result.getInchi());
	    
	}
	
	@Test
	public void ethyne() throws IOException{
		ChemicalBuilder chem = new ChemicalBuilder();
		Atom a1 = chem.addAtom("C");
		Atom a2 = chem.addAtom("C");
		
		chem.addBond(a1, a2, BondType.TRIPLE);
		InChiResult result = Inchi.asStdInchi(chem.build());

		assertEquals("InChI=1S/C2H2/c1-2/h1-2H", result.getInchi());
	    
	}


	@Test
	public void E_1_2_dichloroethene() throws IOException {
		ChemicalBuilder chem = new ChemicalBuilder();
		Atom c1 = chem.addAtom("C", 2.866, -0.250);
		Atom c2 = chem.addAtom("C", 3.732, 0.250);

		Atom cl = chem.addAtom("Cl", 2, 2.500);
		Atom cl2 = chem.addAtom("Cl", 4.598, -0.250);

		chem.addBond(c1, c2, BondType.DOUBLE);
		chem.addBond(c1, cl, BondType.SINGLE);
		chem.addBond(c2, cl2, BondType.SINGLE);
		
		//chem.computeCoordinates(true);
		chem.computeStereo(true);
		
		//InternalUtil.RunnableCode<IOException> lambda = () ->{
				InChiResult result = Inchi.asStdInchi(chem.build(), true);
		assertEquals("InChI=1S/C2H2Cl2/c3-1-2-4/h1-2H/b2-1+", result.getInchi());

	//	};
		//InternalUtil.printDebug(lambda);

	}

	
	@Test
	public void Z_1_2_dichloroethene() throws IOException{
		ChemicalBuilder chem = new ChemicalBuilder();
		Atom a1 = chem.addAtom("C", 2.866, -0.440);
		Atom a2 = chem.addAtom("C", 3.732, 0.060);
		
		Atom a3 = chem.addAtom("Cl", 2, 0.060);
		Atom a4 = chem.addAtom("Cl", 3.732, 1.060);
		
		chem.addBond(a1, a2, BondType.DOUBLE);
		chem.addBond(a1, a3, BondType.SINGLE);
		chem.addBond(a2, a4, BondType.SINGLE);
		
		chem.computeStereo(true);
		
		InChiResult result = Inchi.asStdInchi(chem.build(), true);
		
		assertEquals("InChI=1S/C2H2Cl2/c3-1-2-4/h1-2H/b2-1-", result.getInchi());
		
	}
}
