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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Scanner;

import org.junit.Test;

import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.ChemicalBuilder;
import gov.nih.ncats.molwitch.io.ChemFormat.MolFormatSpecification;
import gov.nih.ncats.molwitch.io.ChemFormat.MolFormatSpecification.CoordinateOptions;

import static org.junit.Assert.*;

public class TestMolParser {

	@Test
	public void getProperties() throws IOException{
		try(InputStream in = getClass().getResourceAsStream("/molFiles/withProperties.sdf")){
			Chemical chem = Chemical.parseMol(TestUtil.toByteArray(in));
			
			assertEquals("InChI=1S/C9H17NO4/c1-7(11)14-8(5-9(12)13)6-10(2,3)4/h8H,5-6H2,1-4H3", chem.getProperty("PUBCHEM_IUPAC_INCHI"));
		
			assertEquals("0.4", chem.getProperty("PUBCHEM_XLOGP3_AA"));
			
			assertEquals("line wrap", 
					"AAADceByOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHgAAAAAACBThgAYCCAMABAAIAACQCAAAAAAAAAAAAAEIAAACABQAgAAHAAAFIAAQAAAkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==", 
					chem.getProperty("PUBCHEM_CACTVS_SUBSKEYS", true));
			
			StringBuilder expected = new StringBuilder();
			expected.append("1").append(System.lineSeparator())
			.append("5").append(System.lineSeparator())
			.append("255");
			assertEquals("multi-line", expected.toString(), chem.getProperty("PUBCHEM_COORDINATE_TYPE"));
		}
	}
	String mol= "\n" + 
			"  CDK     02081909202D\n" + 
			"\n" + 
			" 20 23  0  0  0  0  0  0  0  0999 V2000\n" + 
			"   -3.4411   -0.8871    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"   -1.6363    0.1230    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    1.7654   -0.9142    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    0.8607    0.6105    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    1.7899    0.0910    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    3.5083    0.1096    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    0.9975   -1.5245    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"   -2.4625   -1.5103    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    2.5687    0.5905    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    2.5225   -1.4327    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    0.0000    0.1068    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    4.2834   -0.4470    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"    3.4887   -0.9815    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"   -1.6511   -0.8723    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"   -0.9187   -1.5279    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"   -0.8303    0.5858    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"   -0.8540    1.5279    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"   -3.4224    0.0551    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"   -2.5454    0.6312    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"   -4.2834   -0.3703    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" + 
			"  1 20  1  0  0  0  0 \n" + 
			"  1 18  1  0  0  0  0 \n" + 
			"  2 19  1  0  0  0  0 \n" + 
			"  2 16  1  0  0  0  0 \n" + 
			"  2 14  1  0  0  0  0 \n" + 
			"  3  5  1  0  0  0  0 \n" + 
			"  4  5  1  0  0  0  0 \n" + 
			"  1  8  1  0  0  0  0 \n" + 
			"  3  7  1  0  0  0  0 \n" + 
			" 12 13  1  0  0  0  0 \n" + 
			" 14 15  1  0  0  0  0 \n" + 
			" 16 17  2  0  0  0  0 \n" + 
			"  3 10  1  0  0  0  0 \n" + 
			" 18 19  1  0  0  0  0 \n" + 
			" 11 16  1  0  0  0  0 \n" + 
			"  5  9  1  0  0  0  0 \n" + 
			" 18 20  1  0  0  0  0 \n" + 
			"  6  9  1  0  0  0  0 \n" + 
			"  8 14  1  0  0  0  0 \n" + 
			"  6 13  1  0  0  0  0 \n" + 
			"  6 12  1  0  0  0  0 \n" + 
			"  4 11  1  0  0  0  0 \n" + 
			" 10 13  1  0  0  0  0 \n" + 
			"M  END";
	@Test
	public void testChemicalDoesntAddStereo() throws IOException{
		
		Chemical c= Chemical.parseMol(mol);
		
		assertTrue(!c.toSmiles().contains("@"));
	}
	
	
	@Test
	public void addAtomDoesntMessupCoordsOfOther() throws Exception{
		ChemicalBuilder builder = ChemicalBuilder.createFromMol(mol);
		 builder.addAtom("C", 10, 10);
									
		 Chemical c = builder.build();
		
		 String outputMol = c.toMol(new MolFormatSpecification()
				 .setCoordinateOptions(CoordinateOptions.FORCE_2D));
		 try(Scanner reader = new Scanner(new StringReader(outputMol))){
			 reader.nextLine();
			 reader.nextLine();
			 reader.nextLine();
			 int numAtoms = reader.nextInt();
			 reader.nextLine();
			 assertEquals(c.getAtomCount(), numAtoms);
			 int numXAt0=0, numYAt0=0;
			 for(int i=0; i< numAtoms; i++) {
				 double x = reader.nextDouble();
				 double y = reader.nextDouble();
				 reader.nextLine();
				 if(!doubleIsDifferent(x, 0.0D, 0.0001)) {
					 numXAt0++;
				 }
				 if(!doubleIsDifferent(y, 0.0D, 0.0001)) {
					 numYAt0 ++;
				 }
			 }
			 assertTrue(Integer.toString(numXAt0), numXAt0 <=1);
			 assertTrue(Integer.toString(numYAt0), numYAt0 <=1);
		 }
	}
	@Test
	public void addAtomDoesntMessupCoordsOfOther3D() throws Exception{
		ChemicalBuilder builder = ChemicalBuilder.createFromMol(mol);
		 builder.addAtom("C", 10, 10);
									
		 Chemical c = builder.build();
		
		 String outputMol = c.toMol(new MolFormatSpecification()
				 .setCoordinateOptions(CoordinateOptions.FORCE_3D));
		 try(Scanner reader = new Scanner(new StringReader(outputMol))){
			 reader.nextLine();
			 reader.nextLine();
			 reader.nextLine();
			 int numAtoms = reader.nextInt();
			 reader.nextLine();
			 assertEquals(c.getAtomCount(), numAtoms);
			 int numXAt0=0, numYAt0=0;
			 for(int i=0; i< numAtoms; i++) {
				 double x = reader.nextDouble();
				 double y = reader.nextDouble();
				 reader.nextLine();
				 if(!doubleIsDifferent(x, 0.0D, 0.0001)) {
					 numXAt0++;
				 }
				 if(!doubleIsDifferent(y, 0.0D, 0.0001)) {
					 numYAt0 ++;
				 }
			 }
			 assertTrue(Integer.toString(numXAt0), numXAt0 <=1);
			 assertTrue(Integer.toString(numYAt0), numYAt0 <=1);
		 }
	}
	
	//from junit Assert code that acutally does double delta comp
	static private boolean doubleIsDifferent(double d1, double d2, double delta) {
        if (Double.compare(d1, d2) == 0) {
            return false;
        }
        if ((Math.abs(d1 - d2) <= delta)) {
            return false;
        }

        return true;
    }
}
