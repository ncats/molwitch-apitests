package gov.nih.ncats.witch.tests;

import java.util.BitSet;

import org.junit.Test;

import gov.nih.ncats.witch.fingerprint.Fingerprint;

import static org.junit.Assert.*;
public class TestFingerPrint {

	private static double DELTA = 0.0001D;
	
	
	@Test
	public void fingerprintFromByteArray(){
		Fingerprint expected = fingerPrint(1,1,1,1,1,1,1,1, 0,0,0,0,0,0,0,0, 1,1,1,1,1,1,1,1);
		
		Fingerprint actual = new Fingerprint(new byte[]{(byte) 0xff,0, (byte) 0xff});
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void equals(){
		Fingerprint fp = fingerPrint(1,1,1,1,1,1,1,1,1);
		Fingerprint copy = fingerPrint(1,1,1,1,1,1,1,1,1);
		
		TestUtil.assertEqualAndMatchingHashCode(fp, copy);
	}
	
	
	@Test
	public void popcount(){
		Fingerprint fp = fingerPrint(1,1,0,1,1,0,1,1,1);
		
		assertEquals(7, fp.populationCount());
	}
	
	
	@Test
	public void and(){
		Fingerprint fp1 = fingerPrint(1,0,1,1,1,0,1,1, 0,0,1,0,1,0,0,0, 1,1,1,1,1,1,1,1);
		Fingerprint fp2 = fingerPrint(1,0,1,1,1,1,1,1, 0,0,1,0,0,0,0,0, 1,1,0,1,1,1,1,1);
		
		Fingerprint expected = 
				          fingerPrint(1,0,1,1,1,0,1,1, 0,0,1,0,0,0,0,0, 1,1,0,1,1,1,1,1);
		
		assertEquals(expected, fp1.and(fp2));
	}
	@Test
	public void tanimotoSameShouldBe1(){
		Fingerprint fp = fingerPrint(1,1,1,1,1,1,1,1,1);
		
		double actual =fp.tanimotoSimilarity(fp);
		assertEquals(1, actual, DELTA);
	}
	
	@Test
	public void tanimotoDifferentShouldLessthanOne(){
		Fingerprint fp = fingerPrint(1,1,1,1,1,1,1,1,1);
		Fingerprint fp2 = fingerPrint(1,0,1,1,1,1,0,1,1,1);
		
		double actual =fp.tanimotoSimilarity(fp2);
		assertTrue(actual< 1 );
	}
	
	@Test
	public void tanimotoShortCircuitDifferentShouldReturnEmpty(){
		Fingerprint fp = fingerPrint(1,1,1,1,1,1,1,1,1);
		Fingerprint fp2 = fingerPrint(1,0,1,1,1,1,0,1,1,1);
		
		assertFalse(fp.tanimotoSimilarityShortCircuit(fp2).isPresent());
	}
	
	@Test
	public void tanimotoShortCircuitSameShouldBe1(){
		Fingerprint fp = fingerPrint(1,1,1,1,1,1,1,1,1);
		
		double actual =fp.tanimotoSimilarityShortCircuit(fp).getAsDouble();
		assertEquals(1, actual, DELTA);
	}
	
	
	@Test
	public void hammingDistanceSameFingerPrintShouldBeZero(){
		Fingerprint fp = fingerPrint(1,1,1,1,1,1,1,1,1);
		
		int actual =fp.hammingDistance(fp);
		assertEquals(0, actual);
	}
	
	@Test
	public void hammingDistanceOneBitChangedShouldBe1(){
		Fingerprint fp =               fingerPrint(1,1,1,1,1,1,1,1,1);
		
		int actual =fp.hammingDistance(fingerPrint(1,1,1,1,1,1,1,0,1));
		assertEquals(1, actual);
	}
	
	@Test
	public void hammingDistanceTwoBits(){
		Fingerprint fp =               fingerPrint(1,0,1,1,1,1,1,1,1);
		
		int actual =fp.hammingDistance(fingerPrint(1,1,1,1,1,1,1,0,1));
		assertEquals(2, actual);
	}
	
	@Test
	public void hammingDistanceTwoBitsDifferentSomeBothZero(){
		Fingerprint fp =               fingerPrint(1,0,1,1,0,1,1,1,1);
		
		int actual =fp.hammingDistance(fingerPrint(1,1,1,1,0,1,1,0,1));
		assertEquals(2, actual);
	}
	
	@Test
	public void jaccardDistanceSameFingerPrintShouldBeZero(){
		Fingerprint fp = fingerPrint(1,1,1,1,1,1,1,1,1);
		
		double actual =fp.jaccardDistanceTo(fp);
		assertEquals(0, actual, DELTA);
	}
	
	@Test
	public void jaccardDistanceOneBitDifferent(){
		Fingerprint fp =                    fingerPrint(1,1,1,1,1,1,1,1,1,1);
		
		double actual =fp.jaccardDistanceTo(fingerPrint(1,1,1,1,1,1,0,1,1,1));
		
		double expected = 1 - (9/10D);
		assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void jaccardDistanceCompletelyDifferent(){
		Fingerprint fp =                    fingerPrint(1,1,1,1,1,1,1,1,1,1);
		
		double actual =fp.jaccardDistanceTo(new Fingerprint(new BitSet()));
		
		assertEquals(1, actual, DELTA);
	}
	
	@Test
	public void toBitSet(){
		BitSet expected = BitSetOf(1,1,1,1,1,1,1,1,1,1);
		
		Fingerprint fp = new Fingerprint(expected);
		
		assertEquals(expected, fp.toBitSet());
		
	}
	
	private static Fingerprint fingerPrint(int...values){
		return new Fingerprint(BitSetOf(values));
	}
	private static BitSet BitSetOf(int...values){
		BitSet bits = new BitSet(values.length);
		for(int i=0; i< values.length; i++){
			if(values[i] !=0){
				bits.set(i);
			}
		}
		return bits;
	}
	
	
	@Test
	public void sameFingerprintIsCompatible(){
		Fingerprint fp =                    fingerPrint(1,1,1,1,1,1,1,1,1,1);
		assertTrue(fp.compatible(fp));
	}
	
	@Test
	public void differentFingerprintIsNotCompatible(){
		Fingerprint fp =                    fingerPrint(1,1,1,1,1,1,1,1,1,1);
		Fingerprint fp2 =                   fingerPrint(1,1,1,1,0,1,1,1,1,1);
		assertFalse(fp.compatible(fp2));
	}
	
	@Test
	public void compatibilityIsNotAssociative(){
		Fingerprint fp =                    fingerPrint(1,1,1,1,1,1,1,1,1,1);
		Fingerprint fp2 =                   fingerPrint(1,1,1,1,0,1,1,1,1,1);
		assertTrue(fp2.compatible(fp));
	}
	@Test
	public void differentSizesOnlyComparesUptoTargetLength(){
		Fingerprint fp =                    fingerPrint(1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0);
		Fingerprint fp2 =                   fingerPrint(1,1,1,1,1,1,1,1,1,1);
		//this is false because it has more bits at the end set
		assertFalse(fp.compatible(fp2));
		
		assertTrue(fp2.compatible(fp));
	}
}
