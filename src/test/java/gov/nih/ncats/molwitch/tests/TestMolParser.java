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

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Bond;
import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.ChemicalBuilder;
import gov.nih.ncats.molwitch.io.ChemFormat.MolFormatSpecification;
import gov.nih.ncats.molwitch.io.ChemFormat.MolFormatSpecification.CoordinateOptions;

import static org.junit.Assert.*;

public class TestMolParser {
    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Mol Parser");

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

    @Test
    public void largeManyRings() throws IOException {
	    String mol ="\n" +
                "   JSDraw209182020322D\n" +
                "\n" +
                " 78 90  0  0  1  0              0 V2000\n" +
                "   33.2167  -28.5376    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   34.3601  -29.5987    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   31.7264  -28.9972    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   31.3788  -30.5182    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   33.5637  -27.0168    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   35.0546  -26.5569    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   35.4014  -25.0361    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   36.9224  -24.6891    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   37.5990  -23.2836    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   36.9224  -21.8780    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   35.4014  -21.5310    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   34.1818  -22.5039    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   34.1818  -24.0638    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   37.8013  -20.5892    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   39.3567  -20.7060    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   40.2354  -19.4169    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   41.7908  -19.5337    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   42.4679  -18.1280    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   43.9889  -17.7811    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   43.9889  -16.2211    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   45.2080  -15.2485    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   46.7290  -15.5952    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   47.9958  -14.6850    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   48.3802  -13.1732    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   47.5016  -11.8846    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   47.9618  -10.3937    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   46.8178   -9.3325    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   49.4136   -9.8236    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   48.1248   -8.9449    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   49.4136   -8.2636    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   50.7648   -7.4839    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   50.3609   -5.9772    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   51.1407   -4.6260    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   50.1913   -3.3886    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   52.6473   -4.2221    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   53.9985   -5.0025    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   55.1015   -3.8992    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   56.6085   -4.3031    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   57.0118   -5.8097    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   55.9091   -6.9130    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   54.4024   -6.5091    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   53.6221   -7.8598    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   52.1154   -8.2636    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   52.1154   -9.8236    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   50.7648  -10.6040    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   50.9970  -12.1461    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   49.9362  -13.2895    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   51.0528  -14.3787    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   51.2082  -15.9311    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   50.3295  -17.2199    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   48.8278  -17.6425    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   47.4061  -17.0009    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   46.7290  -18.4066    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   45.2080  -18.7533    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   45.2080  -20.3135    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   43.9889  -21.2861    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   42.4679  -20.9388    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   41.5892  -22.2280    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   40.0338  -22.1111    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   39.1550  -23.4003    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   39.8315  -24.8054    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   58.4419   -6.4335    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   59.8211   -5.7046    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   61.0036   -6.7220    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   60.7135   -8.2551    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   62.4756   -6.2070    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   63.6584   -7.2244    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   62.7657   -4.6742    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   64.0479   -5.5627    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   65.2894   -4.6175    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   64.7738   -3.1453    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   63.2144   -3.1803    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   61.5831   -3.6568    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   60.1111   -4.1720    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   59.0936   -2.9893    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   59.7174   -1.5600    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   57.5346   -3.0477    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   56.8057   -1.6685    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  6  0  0  0\n" +
                "  1  3  1  0  0  0  0\n" +
                "  1  5  1  0  0  0  0\n" +
                "  3  4  1  0  0  0  0\n" +
                "  6  5  2  0  0  0  0\n" +
                "  7  6  1  1  0  0  0\n" +
                "  8  7  1  0  0  0  0\n" +
                "  7 13  1  0  0  0  0\n" +
                "  9  8  1  1  0  0  0\n" +
                "  9 10  1  0  0  0  0\n" +
                " 60  9  1  0  0  0  0\n" +
                " 10 11  1  0  0  0  0\n" +
                " 10 14  1  6  0  0  0\n" +
                " 11 12  1  0  0  0  0\n" +
                " 12 13  2  0  0  0  0\n" +
                " 15 14  1  6  0  0  0\n" +
                " 15 16  1  0  0  0  0\n" +
                " 15 59  1  0  0  0  0\n" +
                " 17 16  1  6  0  0  0\n" +
                " 17 18  1  0  0  0  0\n" +
                " 17 57  1  0  0  0  0\n" +
                " 19 18  1  6  0  0  0\n" +
                " 19 20  1  0  0  0  0\n" +
                " 19 54  1  0  0  0  0\n" +
                " 21 20  2  0  0  0  0\n" +
                " 22 21  1  0  0  0  0\n" +
                " 22 23  1  6  0  0  0\n" +
                " 22 52  1  0  0  0  0\n" +
                " 24 23  1  6  0  0  0\n" +
                " 24 25  1  0  0  0  0\n" +
                " 24 47  1  0  0  0  0\n" +
                " 26 25  1  0  0  0  0\n" +
                " 26 27  1  1  0  0  0\n" +
                " 28 26  1  0  0  0  0\n" +
                " 28 29  1  1  0  0  0\n" +
                " 30 28  1  0  0  0  0\n" +
                " 45 28  1  0  0  0  0\n" +
                " 31 30  1  6  0  0  0\n" +
                " 31 32  1  0  0  0  0\n" +
                " 31 43  1  0  0  0  0\n" +
                " 33 32  1  0  0  0  0\n" +
                " 33 34  1  6  0  0  0\n" +
                " 33 35  1  0  0  0  0\n" +
                " 36 35  1  6  0  0  0\n" +
                " 36 37  1  0  0  0  0\n" +
                " 36 41  1  0  0  0  0\n" +
                " 38 37  1  6  0  0  0\n" +
                " 38 39  1  0  0  0  0\n" +
                " 77 38  1  0  0  0  0\n" +
                " 39 40  1  1  0  0  0\n" +
                " 39 62  1  0  0  0  0\n" +
                " 41 40  1  1  0  0  0\n" +
                " 41 42  1  0  0  0  0\n" +
                " 43 42  1  1  0  0  0\n" +
                " 43 44  1  0  0  0  0\n" +
                " 45 44  1  1  0  0  0\n" +
                " 45 46  1  0  0  0  0\n" +
                " 47 46  1  1  0  0  0\n" +
                " 47 48  1  0  0  0  0\n" +
                " 48 49  1  0  0  0  0\n" +
                " 49 50  2  0  0  0  0\n" +
                " 50 51  1  0  0  0  0\n" +
                " 52 51  1  0  0  0  0\n" +
                " 52 53  1  1  0  0  0\n" +
                " 54 53  1  1  0  0  0\n" +
                " 54 55  1  0  0  0  0\n" +
                " 55 56  2  0  0  0  0\n" +
                " 57 56  1  0  0  0  0\n" +
                " 57 58  1  1  0  0  0\n" +
                " 59 58  1  1  0  0  0\n" +
                " 59 60  1  0  0  0  0\n" +
                " 60 61  1  6  0  0  0\n" +
                " 63 62  1  1  0  0  0\n" +
                " 63 64  1  0  0  0  0\n" +
                " 74 63  1  0  0  0  0\n" +
                " 64 65  1  6  0  0  0\n" +
                " 64 66  1  0  0  0  0\n" +
                " 66 67  1  1  0  0  0\n" +
                " 66 68  1  0  0  0  0\n" +
                " 68 69  1  0  0  0  0\n" +
                " 68 72  1  0  0  0  0\n" +
                " 68 73  1  6  0  0  0\n" +
                " 69 70  1  0  0  0  0\n" +
                " 70 71  1  0  0  0  0\n" +
                " 72 71  1  0  0  0  0\n" +
                " 74 73  1  6  0  0  0\n" +
                " 75 74  1  0  0  0  0\n" +
                " 75 76  1  6  0  0  0\n" +
                " 75 77  1  0  0  0  0\n" +
                " 77 78  1  1  0  0  0\n" +
                "M  END";

	    Chemical c = Chemical.parseMol(mol);
	    long actualAtomsInRing = c.atoms().filter(Atom::isInRing).count();
        long actualBondsInRing = c.bonds().filter(Bond::isInRing).count();
        assertEquals(64, actualAtomsInRing);
        assertEquals(76, actualBondsInRing);
    }
}
