package gov.nih.ncats.witch.tests;

import java.io.IOException;

import org.junit.Test;

import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.io.ChemFormat.MolFormatSpecification;
import gov.nih.ncats.witch.io.ChemFormat.MolFormatSpecification.Version;
import gov.nih.ncats.witch.io.WriterOptionsBuilder;
import gov.nih.ncats.witch.internal.source.MolFileInfo;

import static org.junit.Assert.*;

public class TestWriteAsMolFile {

	private String smiles = "C(Cc1ccccc1)NCc2ccccc2";
	
	@Test
	public void kekulizeMol() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.setName("my_id");
		
		chem.aromatize();
		
//		String actual = chem.formatToString(StandardChemFormats.MOL, new WriterOptionsBuilder()
//																			.kekulize()
//																			.build());
		String actual = chem.toMol();
		MolFileInfo expectedMol = MolFileInfo.parseFrom(TestWriteAsMolFile.class.getResourceAsStream("/molFiles/simple.mol"));
		MolFileInfo actualMol = MolFileInfo.parseFrom(actual);
		
		assertEquals(expectedMol, actualMol);
		
		assertEquals(0, actualMol.getNumberAromaticBonds());
		assertEquals(3, actualMol.getNumberDoubleBonds());
		assertEquals(6, actualMol.getNumberSingleBonds());
		assertEquals(Version.V2000, actualMol.getVersion());
		
		assertEquals("my_id", actualMol.getName());
		
	}
	
	
	
	@Test
	public void kekulizeMol3000() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.setName("my_id");
		chem.aromatize();
		
		String actual = chem.toMol( new MolFormatSpecification(Version.V3000));

		MolFileInfo actualMol = MolFileInfo.parseFrom(actual);
		assertEquals("my_id", actualMol.getName());
		assertEquals(0, actualMol.getNumberAromaticBonds());
		assertEquals(3, actualMol.getNumberDoubleBonds());
		assertEquals(6, actualMol.getNumberSingleBonds());
		assertEquals(Version.V3000, actualMol.getVersion());
		
		
	}
	
	
	
	
	
	
}
