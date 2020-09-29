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
import java.util.List;

import gov.nih.ncats.molwitch.DoubleBondStereochemistry;
import org.junit.Test;

import gov.nih.ncats.molwitch.Bond;
import gov.nih.ncats.molwitch.Chemical;

import static org.junit.Assert.*;
public class TestCisTrans {

	@Test
	public void getCisTrans() throws IOException{
		String resourceName = "/molFiles/testdoublebondconfig.mol";
		Chemical chem;
		try(InputStream in = getClass().getResourceAsStream(resourceName)){
			byte[] bytes = TestUtil.toByteArray(in);
			
			chem = Chemical.parseMol(bytes);
		}
		
		assertEquals(Bond.DoubleBondStereo.E_TRANS, chem.getBond(0).getDoubleBondStereo());
	}
    @Test
    public void getAllDoubleBondStereos() throws IOException{
        String resourceName = "/molFiles/testdoublebondconfig.mol";
        Chemical chem;
        try(InputStream in = getClass().getResourceAsStream(resourceName)){
            byte[] bytes = TestUtil.toByteArray(in);

            chem = Chemical.parseMol(bytes);
        }
        List<DoubleBondStereochemistry> cisTrans = chem.getDoubleBondStereochemistry();
        assertEquals(1, cisTrans.size());

        assertEquals(Bond.DoubleBondStereo.E_TRANS, cisTrans.get(0).getDoubleBond().getDoubleBondStereo());
    }
}
