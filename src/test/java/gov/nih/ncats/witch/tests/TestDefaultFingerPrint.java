package gov.nih.ncats.witch.tests;

import org.junit.Test;

import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.fingerprint.Fingerprint;
import gov.nih.ncats.witch.fingerprint.Fingerprinter;
import gov.nih.ncats.witch.fingerprint.Fingerprinters;

import static org.junit.Assert.*;

import java.io.IOException;


public class TestDefaultFingerPrint {

	@Test
	public void defaultNotNull(){
		assertNotNull(Fingerprinters.getDefault());
	}
	
	@Test
	public void sameChemicalShouldGetIdenticalFingerprint() throws IOException{
		Fingerprinter sut = Fingerprinters.getDefault();
		
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates("c1ccccc1");
		Fingerprint fp1 = sut.computeFingerprint(chem);
		
		Fingerprint fp2 = sut.computeFingerprint(chem);
		
		assertEquals(0, fp1.hammingDistance(fp2));
	}
	
	@Test
	public void differentChemicalShouldGetDifferentFingerprint() throws IOException{
		Fingerprinter sut = Fingerprinters.getDefault();
		
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates("c1ccccc1");
		Fingerprint fp1 = sut.computeFingerprint(chem);
		
		Fingerprint fp2 = sut.computeFingerprint(Chemical.createFromSmilesAndComputeCoordinates("C(CC1=CC=CC=C1)NCC2=CC=CC=C2"));
		
		assertTrue(fp1.hammingDistance(fp2) > 0);
	}
}
