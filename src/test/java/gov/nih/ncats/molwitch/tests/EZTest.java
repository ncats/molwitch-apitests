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

import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.Stereocenter;
import static org.junit.Assert.assertFalse;

public class EZTest {

    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Tetrahedral");

	@Test
	public void check() throws IOException{
		String smiles = "CC(C)[N@+]1([C@@H]2CC[C@H]1C[C@@H](C2)OC(=O)[C@H](c3ccccc3)C4CCCC4)C";
		
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates(smiles);
		assertFalse(chem.getTetrahedrals().isEmpty());
//		for(Stereocenter chirality : chem.getTetrahedrals()){
//			System.out.println(chirality);
//			if("N".equals(chirality.getCenterAtom().getSymbol())){
//				System.out.println(chirality.getChirality());
//			}
//		}
	}
}
