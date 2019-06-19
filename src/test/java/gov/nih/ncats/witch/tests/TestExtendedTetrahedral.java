package gov.nih.ncats.witch.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import gov.nih.ncats.witch.Atom;
import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.Chirality;
import gov.nih.ncats.witch.ExtendedTetrahedralChirality;
import gov.nih.ncats.witch.internal.InternalUtil;
import gov.nih.ncats.witch.Bond.BondType;

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
