package gov.nih.ncats.witch.tests;

import java.io.IOException;

import org.junit.Test;

import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.Stereocenter;

public class EZTest {

	@Test
	public void check() throws IOException{
		String smiles = "CC(C)[N@+]1([C@@H]2CC[C@H]1C[C@@H](C2)OC(=O)[C@H](c3ccccc3)C4CCCC4)C";
		
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		
		for(Stereocenter chirality : chem.getTetrahedrals()){
			System.out.println(chirality);
			if("N".equals(chirality.getCenterAtom().getSymbol())){
				System.out.println(chirality.getChirality());
			}
		}
	}
}
