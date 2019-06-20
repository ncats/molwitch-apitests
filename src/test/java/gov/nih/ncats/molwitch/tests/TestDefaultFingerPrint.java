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

import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.fingerprint.Fingerprint;
import gov.nih.ncats.molwitch.fingerprint.Fingerprinter;
import gov.nih.ncats.molwitch.fingerprint.Fingerprinters;

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
