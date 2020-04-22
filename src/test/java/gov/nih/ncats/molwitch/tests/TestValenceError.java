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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Chemical;
import static org.junit.Assert.*;

//examples borrowed from CDK mailing list 
//https://sourceforge.net/p/cdk/mailman/message/34477296/
@RunWith(Parameterized.class)
public class TestValenceError {


	@Parameters(name = "{1}")
	public static List<Object[]> data(){
		List<Object[]> list = new ArrayList<>();
		list.add(new Object[]{true,"[O-3]"});
		list.add(new Object[]{false,"[O-2]"});
		list.add(new Object[]{false,"[CH2]=O"});
		list.add(new Object[]{true,"[CH3]=O"});
		list.add(new Object[]{false, "C[N+](C)(C)C"});
		return list;
	}
	
	private final boolean hasError;
	private final String smiles;
	public TestValenceError(boolean hasError, String smiles) {
		this.hasError = hasError;
		this.smiles = smiles;
	}
	
	@Test
	public void checkValenceError() throws Exception {
		Chemical c = Chemical.createFromSmiles(smiles);
		boolean actualHasError = c.atoms().peek(a -> System.out.println("atom " + a)).anyMatch(Atom::hasValenceError);
		assertEquals(hasError, actualHasError);
	}
	
}
