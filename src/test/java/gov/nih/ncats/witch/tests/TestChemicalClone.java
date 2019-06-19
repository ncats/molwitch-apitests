package gov.nih.ncats.witch.tests;

import java.io.IOException;

import org.junit.Test;

import gov.nih.ncats.witch.Atom;
import gov.nih.ncats.witch.Chemical;

import static org.junit.Assert.*;

public class TestChemicalClone {

	@Test
	public void cloneShouldHaveSameSmiles() throws IOException{
		Chemical chem = createFromSmiles("c1ccccc1");
		
		Chemical sut = chem.copy();
		
		Atom atom = chem.getAtom(0);
		
		assertEquals(atom, sut.getAtom(0));
	}
	
	private Chemical createFromSmiles(String smiles) throws IOException{
		return Chemical.createFromSmilesAndComputeCoordinates(smiles);
		
	}
}
