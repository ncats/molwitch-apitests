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

package gov.nih.ncats.witch.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.ChemicalBuilder;
import gov.nih.ncats.witch.ChemicalSource;
import gov.nih.ncats.witch.SmilesSource;
import gov.nih.ncats.witch.ChemicalSource.Type;
import gov.nih.ncats.witch.io.ChemicalReader;
import gov.nih.ncats.witch.io.ChemicalReaderFactory;
import gov.nih.ncats.witch.io.StandardChemFormats;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class TestChemicalSourceParsedCorrectly {
	@Rule
	public TemporaryFolder tmpDir = new TemporaryFolder();
	
	@Test
	public void builderShouldHaveNoSource(){
		ChemicalBuilder builder = new ChemicalBuilder();
		builder.addAtom("C");
		
		Chemical c = builder.build();
		assertFalse(c.getSource().isPresent());
	}
	
	@Test
	public void factoryCreateFromSmilesShouldBeSmilesSource() throws IOException{
		String smilesString = "c1ccccc1";
		Chemical c = Chemical.createFromSmilesAndComputeCoordinates(smilesString);
		
		assertEquals(new SmilesSource(smilesString), c.getSource().get());
	}
	
	@Test
	public void smilesFromFileShouldBeSmilesSource() throws IOException{
		
		String smilesString = "c1ccccc1";
		File f =tmpDir.newFile();
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(f))){
			writer.write(smilesString);
			writer.newLine();
		}
		
		try(ChemicalReader reader = ChemicalReaderFactory.newReader(f)){
			assertTrue(reader.canRead());
				Chemical c =reader.read();
				
				assertEquals(new SmilesSource(smilesString), c.getSource().get());
				assertFalse(reader.canRead());
		}
	}
	private static String blankoutProgramLine(String mol) throws IOException{
		StringBuilder builder = new StringBuilder(mol.length());
		
		int i=0;
		try(BufferedReader reader = new BufferedReader(new StringReader(mol))){
			String line;
			while( (line = reader.readLine()) !=null){
				i++;
				if(i==2){
					builder.append(System.lineSeparator());
				}else{
					builder.append(line).append(System.lineSeparator());
				}
			}
			
		}
		return builder.toString();
	}
	
	private static void assertMolEqualsIgnoringProgramLine(String expected, String actual) throws IOException{
		assertEquals(blankoutProgramLine(expected), blankoutProgramLine(actual));
	}
	@Test
	public void chemicalFromSdfShouldHaveSdfAsSource() throws IOException{
		String recordAsString = linesAsString("/molFiles/withProperties.sdf");
		 
		try(ChemicalReader reader = ChemicalReaderFactory.newReader(StandardChemFormats.SDF, new ByteArrayInputStream(recordAsString.getBytes(StandardCharsets.UTF_8)))){
			Chemical c = reader.read();
			ChemicalSource actual = c.getSource().get();
			assertEquals(Type.SDF, actual.getType());
			assertMolEqualsIgnoringProgramLine(recordAsString, actual.getData());
		}
	}
	@Test
	public void chemicalFromMolShouldHaveMolAsSource() throws IOException{
		String recordAsString = linesAsString("/molFiles/simple.mol");
		
		try(ChemicalReader reader = ChemicalReaderFactory.newReader(StandardChemFormats.MOL, new ByteArrayInputStream(recordAsString.getBytes(StandardCharsets.UTF_8)))){
			Chemical c = reader.read();
			ChemicalSource actual = c.getSource().get();
			assertEquals(Type.MOL, actual.getType());
			assertMolEqualsIgnoringProgramLine(recordAsString, actual.getData());
		}
	}
	
	
	private String linesAsString(String resourcePath) throws IOException{
		try(BufferedReader r = new BufferedReader(new InputStreamReader(TestChemicalSourceParsedCorrectly.class.getResourceAsStream(resourcePath)));
				Stream<String> lines =  r.lines()){
			return lines.collect(Collectors.joining(System.lineSeparator()));
		}
	}
}
