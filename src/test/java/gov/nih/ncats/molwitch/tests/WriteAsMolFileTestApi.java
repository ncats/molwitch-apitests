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

import gov.nih.ncats.molwitch.io.ChemFormat;
import gov.nih.ncats.molwitch.tests.contract.ApiContract;
import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.io.ChemFormat.MolFormatSpecification;
import gov.nih.ncats.molwitch.io.ChemFormat.MolFormatSpecification.Version;
import gov.nih.ncats.molwitch.internal.source.MolFileInfo;

import static org.junit.Assert.*;

public class WriteAsMolFileTestApi {
    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Write Mol");

    private String smiles = "C(Cc1ccccc1)NCc2ccccc2";
	
	@Test
	public void forceKekulizeMol() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.setName("my_id");
		
		chem.aromatize();

		String actual = chem.toMol(new MolFormatSpecification().setKekulization(ChemFormat.KekulizationEncoding.KEKULE));
		MolFileInfo expectedMol = MolFileInfo.parseFrom(WriteAsMolFileTestApi.class.getResourceAsStream("/molFiles/simple.mol"));
		MolFileInfo actualMol = MolFileInfo.parseFrom(actual);
		
		assertEquals(expectedMol, actualMol);
		
		assertEquals(0, actualMol.getNumberAromaticBonds());
		assertEquals(3, actualMol.getNumberDoubleBonds());
		assertEquals(6, actualMol.getNumberSingleBonds());
		assertEquals(Version.V2000, actualMol.getVersion());
		
		assertEquals("my_id", actualMol.getName());
		
	}

	@Test
	public void asIsKekulizeMol() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.setName("my_id");

		chem.kekulize();

		String actual = chem.toMol(new MolFormatSpecification().setKekulization(ChemFormat.KekulizationEncoding.AS_IS));
		MolFileInfo expectedMol = MolFileInfo.parseFrom(WriteAsMolFileTestApi.class.getResourceAsStream("/molFiles/simple.mol"));
		MolFileInfo actualMol = MolFileInfo.parseFrom(actual);

		assertEquals(expectedMol, actualMol);

		assertEquals(0, actualMol.getNumberAromaticBonds());
		assertEquals(3, actualMol.getNumberDoubleBonds());
		assertEquals(6, actualMol.getNumberSingleBonds());
		assertEquals(Version.V2000, actualMol.getVersion());

		assertEquals("my_id", actualMol.getName());

	}

	@Test
	public void aromatizeMol() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.setName("my_id");

		chem.kekulize();

		String actual = chem.toMol(new MolFormatSpecification().setKekulization(ChemFormat.KekulizationEncoding.FORCE_AROMATIC));
//		MolFileInfo expectedMol = MolFileInfo.parseFrom(TestWriteAsMolFile.class.getResourceAsStream("/molFiles/simple.mol"));
		MolFileInfo actualMol = MolFileInfo.parseFrom(actual);

//		assertEquals(expectedMol, actualMol);

		assertEquals(6, actualMol.getNumberAromaticBonds());
		assertEquals(0, actualMol.getNumberDoubleBonds());
		assertEquals(3, actualMol.getNumberSingleBonds());
		assertEquals(Version.V2000, actualMol.getVersion());

		assertEquals("my_id", actualMol.getName());

	}
	@Test
	public void aromatizeAsISMol() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.setName("my_id");

		chem.aromatize();

		String actual = chem.toMol(new MolFormatSpecification().setKekulization(ChemFormat.KekulizationEncoding.AS_IS));
		MolFileInfo expectedMol = MolFileInfo.parseFrom(WriteAsMolFileTestApi.class.getResourceAsStream("/molFiles/simple.mol"));
		MolFileInfo actualMol = MolFileInfo.parseFrom(actual);

//		assertEquals(expectedMol, actualMol);

		assertEquals(6, actualMol.getNumberAromaticBonds());
		assertEquals(0, actualMol.getNumberDoubleBonds());
		assertEquals(3, actualMol.getNumberSingleBonds());
		assertEquals(Version.V2000, actualMol.getVersion());

		assertEquals("my_id", actualMol.getName());

	}
	
	@Test
	@ApiContract( message = "can't write v3000")
	public void kekulizeMol3000() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		chem.setName("my_id");
		chem.aromatize();
		
		String actual = chem.toMol( new MolFormatSpecification(Version.V3000)
									.setKekulization(ChemFormat.KekulizationEncoding.KEKULE));

		MolFileInfo actualMol = MolFileInfo.parseFrom(actual);
		assertEquals("my_id", actualMol.getName());
		assertEquals(0, actualMol.getNumberAromaticBonds());
		assertEquals(3, actualMol.getNumberDoubleBonds());
		assertEquals(6, actualMol.getNumberSingleBonds());
		assertEquals(Version.V3000, actualMol.getVersion());
		
		
	}
	
	
	
	
	
	
}
