package gov.nih.ncats.witch.tests;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.io.ChemicalReader;
import gov.nih.ncats.witch.io.ChemicalReaderFactory;

public class ParseWeirdParityTest {

	
	@Test
	public void parse() throws IOException{
		try(InputStream in = getClass().getResourceAsStream("/molFiles/weirdParity.mol");
			ChemicalReader reader = ChemicalReaderFactory.newReader(in);
			){
			Chemical chem = reader.read();
			
			
		}
	}
}
