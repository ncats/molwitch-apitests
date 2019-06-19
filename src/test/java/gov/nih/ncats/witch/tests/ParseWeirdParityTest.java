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

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.io.ChemicalReader;
import gov.nih.ncats.witch.io.ChemicalReaderFactory;

public class ParseWeirdParityTest {

	
	@Test
	public void parse() throws IOException{
		try(InputStream in = getClass().getResourceAsStream("/molFiles/weirdParity.mol");
			ChemicalReader reader = ChemicalReaderFactory.newReader(in);
			){
			Chemical chem = reader.read();
			
			
		}
	}
}
