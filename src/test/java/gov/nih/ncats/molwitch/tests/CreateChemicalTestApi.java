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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import gov.nih.ncats.molwitch.io.*;
import gov.nih.ncats.molwitch.tests.contract.ApiContract;
import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Bond;
import gov.nih.ncats.molwitch.Bond.BondType;
import gov.nih.ncats.molwitch.BondTable;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.ChemicalBuilder;
import gov.nih.ncats.molwitch.io.ChemFormat.ChemFormatWriterSpecification;
import gov.nih.ncats.molwitch.io.ChemFormat.HydrogenEncoding;
import gov.nih.ncats.molwitch.io.ChemFormat.KekulizationEncoding;
import gov.nih.ncats.molwitch.io.ChemFormat.MolFormatSpecification;
import gov.nih.ncats.molwitch.io.ChemFormat.SdfFormatSpecification;

public class CreateChemicalTestApi {
    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Create Chemical");


    @Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Rule
	public TemporaryFolder temp = new TemporaryFolder();
	
	private String smiles = "C(Cc1ccccc1)NCc2ccccc2";
	
	
	private static Chemical createFromSmiles(String smiles) throws IOException{
		return ChemicalBuilder.createFromSmiles(smiles).build();
	}
	
	
	private static Chemical createFrom(String format, File f) throws IOException{
		try(ChemicalReader reader = ChemicalReaderFactory.newReader(format, f)){
			return reader.read();
		}catch(IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	@Test
	public void getAtomIndexWorks() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		
		int[] indexes = new int[chem.getAtomCount()];
		assertTrue(indexes.length > 0);
		for(Atom a : chem.getAtoms()) {
			int index = a.getAtomIndexInParent();
			indexes[index] = index;
		}
		
		for(int i=0; i< indexes.length; i++) {
			assertEquals(i, indexes[i]);
		}
		
	}
	
	@Test
	public void aromatizedSmilesViaOption() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.kekulize();
		
		assertSmilesMatch(smiles, chem.toSmiles(new ChemFormat.SmilesFormatWriterSpecification()
												.setKekulization(KekulizationEncoding.FORCE_AROMATIC)));
		
	}
	
	@Test
	public void getMass() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		//depending on the implementation of the submodule
		//we might get a fractional number if we account
		//for naturally occurring isotopes or not
		//so just make sure the whole number part is right
		
		assertEquals(211, (int)chem.getMass());
	}
	
	@Test
	public void kekulizedSmilesViaOption() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.aromatize();
		
		assertSmilesMatch("C(CC1=CC=CC=C1)NCC2=CC=CC=C2", chem.toSmiles(new ChemFormat.SmilesFormatWriterSpecification()
											.setKekulization(KekulizationEncoding.KEKULE)));
		
	}
	
	@Test
	public void makeImplicitHydrogenExplicit() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.makeHydrogensExplicit();
		
		Atom c = chem.getAtom(0);
		assertEquals(6, c.getAtomicNumber());
		
		List<? extends Bond> bonds = c.getBonds();
		
		assertEquals(4, bonds.size());
		int numCbonds=0;
		int numHBonds =0;
		
		for(Bond b : bonds){
			Atom other =b.getOtherAtom(c);
			if(other.getAtomicNumber() ==1){
				numHBonds++;
			}else{
				numCbonds++;
			}
		}
		
		assertEquals(2, numHBonds);
		assertEquals(2, numCbonds);
	}
	
	@Test
	public void explicitHydrogrenSmilesViaOption() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.aromatize();
	
		
		String actual= chem.toSmiles(new ChemFormat.SmilesFormatWriterSpecification()
									.setHydrogenEncoding(HydrogenEncoding.MAKE_EXPLICIT));
		//can't do a String comparison because there is more than 1 way to print the smiles string with [H]s
		//depending on how you branch.
		
		//quick and dirty way to check correctness is to just count the # of hydrogens is correct
		//rely on other tests to make sure smiles are right.
		int count = getCountOf("[H]", actual);
		assertEquals(actual, 17, count);
		
	}
	
	private int getCountOf(String string, String actual) {
		Pattern pattern = Pattern.compile(Pattern.quote(string));
		
		Matcher matcher = pattern.matcher(actual);
		int count=0;
		while(matcher.find()){
			count++;
		}
		return count;
	}

	@Test
	public void aromaticSmileIsDefault() throws IOException{
		Chemical chem = createFromSmiles(smiles);
		chem.kekulize();

//		System.out.println("in kekulized " + chem.formatToString(StandardChemFormats.SMILES));
		assertSmilesMatch(smiles, chem.toSmiles());
//				chem.formatToString(StandardChemFormats.SMILES, new WriterOptionsBuilder()
//																		.kekulize()
//																		.build()));
		
	}
	

	
	@Test
	public void toSmilesFile() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.aromatize();
		
		File f = writeToFile(new ChemFormat.SmilesFormatWriterSpecification(), chem);
		
		Chemical actual = createFrom(StandardChemFormats.SMILES, f);
		actual.aromatize();
		assertSmilesMatch(smiles, actual.toSmiles(new ChemFormat.SmilesFormatWriterSpecification().setKekulization(KekulizationEncoding.FORCE_AROMATIC)));
	}
	
	@Test
	public void toMolFile() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.aromatize();
		
		File f = writeToFile(new MolFormatSpecification(), chem);
		
		Chemical actual = createFrom(StandardChemFormats.MOL, f);
		actual.aromatize();
		assertSmilesMatch(smiles, chem.toSmiles());
	}
	
	@Test
	public void getIdFromMolFile() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.aromatize();
		
		chem.setName("myId");
		
		File f = writeToFile(new MolFormatSpecification(), chem);
		Chemical actual = createFrom(StandardChemFormats.MOL, f);
		assertEquals("myId", actual.getName());
	}
	
	@Test
	public void multipleSdfFile() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.aromatize();
		
		Chemical chem2 = Chemical.createFromSmilesAndComputeCoordinates("CN=C=O");
		chem2.aromatize();
		
		File f = writeToFile(new SdfFormatSpecification(), chem, chem2);
		
		try(ChemicalReader reader = ChemicalReaderFactory.newReader(StandardChemFormats.SDF, f)){
			assertTrue(reader.canRead());
			Chemical actual1 = reader.read();
			actual1.aromatize();
			assertSmilesMatch(smiles, actual1.toSmiles(new ChemFormat.SmilesFormatWriterSpecification()
														.setKekulization(KekulizationEncoding.FORCE_AROMATIC)));
			
			assertTrue(reader.canRead());
			assertSmilesMatch("CN=C=O", nextRead(reader).toSmiles());
			
			assertFalse(reader.canRead());
		}
		
	}

	private Chemical nextRead(ChemicalReader reader) throws IOException {
		return reader.read();
	}

	@Test
	public void multipleMolFileShouldThrowIOExceptionWhenWritingSecondChemical() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.aromatize();
		
		File f = temp.newFile();
		
		try(ChemicalWriter writer = ChemicalWriterFactory.newWriter(new MolFormatSpecification()
				, f)) {
			writer.write(chem);
			try {
				writer.write(chem);
				Assert.fail("should have thown an IO Exception");
			} catch (IOException e) {
				//expected can't use expectedException anymore
				//because the API checker swallows all exceptions when processing
			}
		}
			
		
		
	}
	
	@Test
	public void toKekulizedSmilesFile() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.kekulize();
		
		File f = writeToFile(new ChemFormat.SmilesFormatWriterSpecification().setKekulization(KekulizationEncoding.KEKULE), chem);
		String line= Files.lines(f.toPath()).findFirst().get();
		assertTrue(line, line.contains("="));
		Chemical actual = createFrom(StandardChemFormats.SMILES, f);
		actual.aromatize();
		assertSmilesMatch(smiles, actual.toSmiles());
	}
	
	@Test
	@ApiContract(category = "Atom Map", message = "Can't Set Atom Map")
	public void setAtomMap() throws IOException {
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		
		chem.getAtom(4).setAtomToAtomMap(8);
		
		assertTrue(chem.getAtom(4).getAtomToAtomMap().isPresent());
		assertEquals(8, chem.getAtom(4).getAtomToAtomMap().getAsInt());
	}
	@Test
	@ApiContract(category = "Atom Map", message = "Can't Clear Atom Map")
	public void notSetAtomMap() throws IOException {
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		
		chem.getAtom(4).clearAtomToAtomMap();
		assertFalse(chem.getAtom(4).getAtomToAtomMap().isPresent());
	}
	@Test
	@ApiContract(category = "Atom Map", message = "Can't Set Atom Map")
	public void clearAtomMap() throws IOException {
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		
		chem.getAtom(4).setAtomToAtomMap(8);
		
		assertTrue(chem.getAtom(4).getAtomToAtomMap().isPresent());
		assertEquals(8, chem.getAtom(4).getAtomToAtomMap().getAsInt());
		
		chem.getAtom(4).clearAtomToAtomMap();
		assertFalse(chem.getAtom(4).getAtomToAtomMap().isPresent());
	}
	
	private void assertSmilesMatch(String expected, String actual){
		if(!expected.equals(actual)){
			
			assertSmilesCloseEnough(expected, actual);
			return;
		}
	}

	private void assertSmilesCloseEnough(String expected, String actual) {
		assertEquals(new SmilesInfo(expected), new SmilesInfo(actual));
	}
	
	private static class SmilesInfo{
		Map<Character, Counter> expectedCounters = new HashMap<>();
		
		int expectedNumberOfRings=0;
		int expectedNumDoubleBonds =0;
		
		public SmilesInfo(String expected) {
		Deque<Character> ringStack = new ArrayDeque<>();
		
		for(int i=0; i< expected.length(); i++) {
			char c = expected.charAt(i);
			switch(c) {
			case '=' : expectedNumDoubleBonds ++;
							break;
		
		
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9' : {
				if(!ringStack.isEmpty() && ringStack.peek().equals(Character.valueOf(c))) {
						expectedNumberOfRings++;
						ringStack.pop();
					
				}else {
					ringStack.push(c);
				}
				break;
			}
			
			default :
				expectedCounters.computeIfAbsent(c, k-> new Counter()).increment();
			}
			
		}
		
		
	}
		
	
	@Override
		public String toString() {
			return "SmilesInfo [expectedCounters=" + expectedCounters + ", expectedNumberOfRings="
					+ expectedNumberOfRings + ", expectedNumDoubleBonds=" + expectedNumDoubleBonds + "]";
		}


	@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((expectedCounters == null) ? 0 : expectedCounters.hashCode());
			result = prime * result + expectedNumDoubleBonds;
			result = prime * result + expectedNumberOfRings;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SmilesInfo other = (SmilesInfo) obj;
			if (expectedCounters == null) {
				if (other.expectedCounters != null)
					return false;
			} else if (!expectedCounters.equals(other.expectedCounters))
				return false;
			if (expectedNumDoubleBonds != other.expectedNumDoubleBonds)
				return false;
			if (expectedNumberOfRings != other.expectedNumberOfRings)
				return false;
			return true;
		}

	private static class Counter{
		int count;
		public Counter() {
			this(0);
		}
		public Counter(int count) {
			this.count = count;
		}

		public void increment() {
			count++;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + count;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Counter other = (Counter) obj;
			if (count != other.count)
				return false;
			return true;
		}
		
		
	}
	}
	
	private File writeToFile(ChemFormatWriterSpecification spec, Chemical...chemicals) throws IOException {
		File f = temp.newFile();
		
		try(ChemicalWriter writer = ChemicalWriterFactory.newWriter(spec, f)){
		
			for(Chemical chem : chemicals){
				writer.write(chem);
			}
		}

		return f;
	}

	@Test
	public void createEmptyChemical(){
		Chemical chem = new Chemical();
		assertEquals(0, chem.getAtomCount());
		assertEquals(0, chem.getBondCount());
		
		assertEquals(0, chem.getMass(), TestUtil.DELTA);
	}
	
	@Test
	public void addSingleAtomBySymbol(){
		Chemical chem = new Chemical();
		
		Atom atom = chem.addAtom("C");
		
		assertEquals(1, chem.getAtomCount());
		assertEquals( 0, chem.indexOf(atom));
		
		assertEquals(6, atom.getAtomicNumber());
		
	}
	
	@Test
	public void addSingleAtomByAtomicNumber(){
		Chemical chem = new Chemical();
		
		Atom atom = chem.addAtomByAtomicNum(6);
		
		assertEquals(1, chem.getAtomCount());
		assertEquals( 0, chem.indexOf(atom));
		
		assertEquals(6, atom.getAtomicNumber());
		assertEquals("C", atom.getSymbol());
		
	}
	
	@Test
	public void createFromScratch() throws IOException{
		Chemical chem = new Chemical();
		//CN=C=O
		Atom c1 = chem.addAtom("C");
		Atom n = chem.addAtom("N");
		Atom c2 = chem.addAtom("C");
		
		Atom o = chem.addAtom("O");
		
		Bond b1 = chem.addBond(c1, n, BondType.SINGLE);
		
		Bond b2 = chem.addBond(n, c2, BondType.DOUBLE);
		
		Bond b3 = chem.addBond(c2, o, BondType.DOUBLE);
		
		
		assertEquals(4, chem.getAtomCount());
		assertEquals(3, chem.getBondCount());
		
		assertEquals(Arrays.asList(b1) , c1.getBonds());
		
		assertEquals(Arrays.asList(b1, b2) , n.getBonds());
		
		assertEquals(Arrays.asList(b2, b3) , c2.getBonds());
		
		assertEquals(Arrays.asList(b3) , o.getBonds());
		
		assertEquals(c1, chem.getAtom(0));
		assertEquals(n, chem.getAtom(1));
		assertEquals(c2, chem.getAtom(2));
		assertEquals(o, chem.getAtom(3));
		
		
		assertEquals(b1, chem.getBond(0));
		assertEquals(b2, chem.getBond(1));
		assertEquals(b3, chem.getBond(2));
		
		BondTable bondTable = chem.getBondTable();
		
		assertTrue(bondTable.bondExists(0, 1));
		assertTrue(bondTable.bondExists(1, 0));
		for(int i=2 ;i< 4; i++){
			assertFalse(bondTable.bondExists(0, i));
			assertFalse(bondTable.bondExists(i, 0));
		}
		
		assertTrue(bondTable.bondExists(1, 2));
		assertTrue(bondTable.bondExists(2, 1));
		assertFalse(bondTable.bondExists(1, 3));
		assertFalse(bondTable.bondExists(3, 1));
		
		assertTrue(bondTable.bondExists(2, 3));
		assertTrue(bondTable.bondExists(3, 2));

	}
	
	@Test
	public void createAromaticRing() throws IOException{
		Chemical expected = Chemical.createFromSmilesAndComputeCoordinates("c1ccccc1");
		
		ChemicalBuilder builder = new ChemicalBuilder();
		for(Atom a : expected.getAtoms()) {
			builder.addAtom(a.getSymbol());
		}
		for(Bond b : expected.getBonds()) {
			builder.addBond(builder.getAtom(expected.indexOf(b.getAtom1())),
					
					builder.getAtom(expected.indexOf(b.getAtom2())),
					BondType.AROMATIC);
		}
		
		Chemical actual = builder.build();
		for(Bond b : actual.getBonds()) {
			assertTrue(b.isAromatic());
			assertTrue(b.isInRing());
		}
		for(Atom a : actual.getAtoms()) {
			assertTrue(a.isInRing());
		}
		assertEquals("c1ccccc1", actual.toSmiles(new ChemFormat.SmilesFormatWriterSpecification()
											.setKekulization(KekulizationEncoding.FORCE_AROMATIC)));
	}
	
	@Test
	public void linearChemicalIsInRingShouldBeFalse() throws Exception{
		Chemical expected = Chemical.createFromSmilesAndComputeCoordinates("CN=C=O");
		
		for(Atom a : expected.getAtoms()) {
			assertFalse(a.isInRing());
		}
		for(Bond b : expected.getBonds()) {
			assertFalse(b.isInRing());
		}
	}
	
	@Test
	public void noNameSetShouldDefaultToNull(){
		assertNull( new Chemical().getName());
	}
	
	@Test
	public void noNameSetFromSmilesShouldBeNull() throws IOException{
		assertNull( Chemical.createFromSmilesAndComputeCoordinates("c1ccccc1").getName());
	}
	
	@Test
	public void setName(){
		Chemical chem = new Chemical();
		chem.setName("name");
		
		assertEquals("name", chem.getName());
	}
	
	@Test
	public void setNameSetFromSmiles() throws IOException{
		assertEquals("foo", Chemical.createFromSmilesAndComputeCoordinates("c1ccccc1 foo").getName());
	}
	
	@Test
	public void propertyNotSetShouldReturnNull(){
		Chemical chem = new Chemical();
		assertNull(chem.getProperty("foo"));
	}
	
	@Test(expected = NullPointerException.class)
	public void setPropertyToNullKeyShouldThrowNPE(){
		Chemical chem = new Chemical();
		chem.setProperty(null, "bar");
	}
	
	@Test(expected = NullPointerException.class)
	public void setPropertyToValueKeyShouldThrowNPE(){
		Chemical chem = new Chemical();
		chem.setProperty("foo", null);
	}
	@Test(expected = NullPointerException.class)
	public void getPropertyNullKeyShouldThrowNPE(){
		Chemical chem = new Chemical();
		chem.getProperty(null);
	}
	
	@Test
	public void getProperty(){
		Chemical chem = new Chemical();
		chem.setProperty("foo", "bar");
		assertEquals("bar", chem.getProperty("foo"));
	}

	@Test
	public void removeProperty(){
		Chemical chem = new Chemical();
		chem.setProperty("foo", "bar");
		assertEquals("bar", chem.getProperty("foo"));
		chem.removeProperty("foo");

		assertNull(chem.getProperty("foo"));

	}
	
	@Test
	public void overwritePropertyWithNewValue(){
		Chemical chem = new Chemical();
		chem.setProperty("foo", "bar");
		
		chem.setProperty("foo", "aaaa");
		assertEquals("aaaa", chem.getProperty("foo"));
	}
	
	@Test
	public void multipleProperties(){
		Chemical chem = new Chemical();
		chem.setProperty("foo", "bar");
		chem.setProperty("asdf", "bbbbbbbb");
		
		assertEquals("bar", chem.getProperty("foo"));
		assertEquals("bbbbbbbb", chem.getProperty("asdf"));
	}
	
	@Test
	public void getAllProperties(){
		Chemical chem = new Chemical();
		chem.setProperty("foo", "bar");
		chem.setProperty("asdf", "bbbbbbbb");
		
		Map<String,String> actual = chem.getProperties();
		
		Map<String,String> expected = new HashMap<>();
		
		expected.put("foo", "bar");
		expected.put("asdf", "bbbbbbbb");
		
		assertEquals(expected, actual);
		
	}
	
	
	@Test
	public void trans() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates("F/C=C/F");
		
		Bond bond = chem.getBond(1);
		assertEquals(Bond.DoubleBondStereo.E_TRANS, bond.getDoubleBondStereo());
	}
	
	@Test
	public void removeAtomThenReAdd() throws Exception{
		Chemical c=Chemical.createFromSmiles("CCCCC");
		Atom remAt=c.getAtom(3);
		List<Bond> removedBonds = new ArrayList<>();
		List<BondInfo> bondInfos = new ArrayList<>();

		for(Bond b : remAt.getBonds()){
			BondInfo info = new BondInfo();
			info.otherAtom = b.getOtherAtom(remAt);
			info.bondOrder = b.getBondType();
			bondInfos.add(info);
		}
		
		c.removeAtom(remAt);
		
		Atom newAtom = c.addAtom(remAt);
		for(BondInfo b : bondInfos){
			c.addBond(newAtom, b.otherAtom, b.bondOrder);
		}
		assertEquals("CCCCC", c.toSmiles(new ChemFormat.SmilesFormatWriterSpecification().
													setHydrogenEncoding(HydrogenEncoding.MAKE_IMPLICIT)));

	}

	private static class BondInfo{
		public Atom otherAtom;
		public BondType bondOrder;
	}
}
