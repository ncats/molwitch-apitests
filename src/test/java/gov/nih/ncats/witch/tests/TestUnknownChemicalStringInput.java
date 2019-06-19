package gov.nih.ncats.witch.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import org.junit.Test;
import static org.junit.Assert.*;
import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.ChemicalSource;

public class TestUnknownChemicalStringInput {

	@Test
	public void smiles() throws IOException{
		Chemical chemical = Chemical.parse("COC1=CC=C(O)C2=C(O)C(C)=C3OC(C)(O)C(=O)C3=C12");
		assertEquals(ChemicalSource.Type.SMILES, chemical.getSource().get().getType());
	}
	
	@Test
	public void smarts() throws IOException{
		Chemical chemical = Chemical.parse("[#7,#8]~C1=c2c3c(OC([#6])(O)C3=O)cc(O)c2=C(O)\\C=C/1");
		assertEquals(ChemicalSource.Type.SMARTS, chemical.getSource().get().getType());
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
