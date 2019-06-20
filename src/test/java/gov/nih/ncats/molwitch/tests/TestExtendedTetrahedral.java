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
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.Chirality;
import gov.nih.ncats.molwitch.ExtendedTetrahedralChirality;
import gov.nih.ncats.molwitch.internal.InternalUtil;
import gov.nih.ncats.molwitch.Bond.BondType;

import static org.junit.Assert.*;

public class TestExtendedTetrahedral {

	@Before
	public void toggleDebugOn() {
		InternalUtil.on();
	}
	
	@Before
	public void toggleDebugOff() {
		InternalUtil.off();
	}
	
	@Test
	public void hasAExtendedTetrahedralExplicitH() throws IOException{
		try(InputStream in= getClass().getResourceAsStream("/molFiles/ChEBI_38402.mol");
			
				){
			Chemical c= Chemical.parseMol(in);
			c.makeHydrogensExplicit();
			
			List<ExtendedTetrahedralChirality> list = c.getExtendedTetrahedrals();
			
			assertEquals(1, list.size());
			//should look like this:
			
			//
			// C           H
			//  \         /
			//   C = C = C
			//  /         \
			// H            C
			//
			//chirality is counter clockwise starting at 11 oclock
			
			ExtendedTetrahedralChirality extendedTetrahdral = list.get(0);
			
			assertEquals(Chirality.S, extendedTetrahdral.getChirality());
			
			assertEquals("C", extendedTetrahdral.getCenterAtom().getSymbol());
			assertEquals("C,C", extendedTetrahdral.getTerminalAtoms().stream()
					      .map(Atom::getSymbol)
					      .collect(Collectors.joining(",")));
			
			for(Atom a:  extendedTetrahdral.getTerminalAtoms()) {
				assertEquals( BondType.DOUBLE, a.bondTo(extendedTetrahdral.getCenterAtom()).get().getBondType());
			}
			
			List<Atom> peripheralAtoms = extendedTetrahdral.getPeripheralAtoms();
			assertEquals(4, peripheralAtoms.size());
			
		
			assertEquals( "C", peripheralAtoms.get(0).getSymbol());
			assertEquals( "H", peripheralAtoms.get(1).getSymbol());
			assertEquals( "C", peripheralAtoms.get(2).getSymbol());
			assertEquals( "H", peripheralAtoms.get(3).getSymbol());
			
		}
	}
	
	@Test
	public void hasAExtendedTetrahedralImplicitH() throws IOException{
		try(InputStream in= getClass().getResourceAsStream("/molFiles/ChEBI_38402.mol");
			
				){
			Chemical c= Chemical.parseMol(in);
			
			List<ExtendedTetrahedralChirality> list = c.getExtendedTetrahedrals();
			
			assertEquals(1, list.size());
			//should look like this:
			
			//
			// C           H
			//  \         /
			//   C = C = C
			//  /         \
			// H            C
			//
			//chirality is counter clockwise starting at 11 oclock
			
			
			//when its implicit H, the peripheralAtoms will have the terminal C's instead
			//when needed
			
			ExtendedTetrahedralChirality extendedTetrahdral = list.get(0);
			
			assertEquals(Chirality.S, extendedTetrahdral.getChirality());
			
			assertEquals("C", extendedTetrahdral.getCenterAtom().getSymbol());
			assertEquals("C,C", extendedTetrahdral.getTerminalAtoms().stream()
					      .map(Atom::getSymbol)
					      .collect(Collectors.joining(",")));
			
			for(Atom a:  extendedTetrahdral.getTerminalAtoms()) {
				assertEquals( BondType.DOUBLE, a.bondTo(extendedTetrahdral.getCenterAtom()).get().getBondType());
			}
			
			List<Atom> peripheralAtoms = extendedTetrahdral.getPeripheralAtoms();
			assertEquals(4, peripheralAtoms.size());
			
			System.out.println(peripheralAtoms);
			
			assertEquals( "C", peripheralAtoms.get(0).getSymbol());
			assertEquals( "H", peripheralAtoms.get(1).getSymbol());
			assertEquals( "C", peripheralAtoms.get(2).getSymbol());
			assertEquals( "C", peripheralAtoms.get(3).getSymbol());
			
			assertEquals(peripheralAtoms.get(3), extendedTetrahdral.getTerminalAtoms().get(1));
			assertEquals(1, peripheralAtoms.get(3).getImplicitHCount());
			
			
		}
	}
}
