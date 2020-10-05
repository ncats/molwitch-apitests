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
import gov.nih.ncats.molwitch.ChemicalBuilder;
public class ProblematicSmilesTestApi {
    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Problematic Smiles");

//Cl.COc4ccc5nc1c(CC[N@]3C[C@H]2C[C@@H]1[C@@H]3[C@H](C2)CC)c5c4    NCGC00247731-01
	
	@Test
	public void parse5memberRingWithN() throws IOException{
		//CDK throws an exception with this lowercase n
		//the Chemkit wrapper will replace it with [nH] if it fails in CDK only
		Chemical.createFromSmilesAndComputeCoordinates("COc4ccc5nc1c(CC[N@]3C[C@H]2C[C@@H]1[C@@H]3[C@H](C2)CC)c5c4");
	}
	
	@Test
	public void fiveMemberRing() throws IOException{
		Chemical.createFromSmiles("CC(C)N1CCN(CC1)C(=O)[C@H](Cc2ccc3nc(=O)oc3c2)NC(=O)N4CC[C@@]5(CC4)NC(=O)Nc6ccccc56");
	}
	
	@Test
	public void fiveMemberRingBuilder() throws IOException{
		ChemicalBuilder.createFromSmiles("CC(C)N1CCN(CC1)C(=O)[C@H](Cc2ccc3nc(=O)oc3c2)NC(=O)N4CC[C@@]5(CC4)NC(=O)Nc6ccccc56")
				.build();
	}
}
