/*
 * NCATS-WITCH-APITESTS
 *
 * Copyright 2020 NIH/NCATS
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

package gov.nih.ncats.molwitch.tests.inchi;

import gov.nih.ncats.molwitch.Chemical;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class InchiTest {

    private String expectedInchi, mol;

    public InchiTest(String expectedInchi, String mol) {
        this.expectedInchi = expectedInchi;
        this.mol = mol;
    }

    @Parameterized.Parameters
    public static List<Object[]> data(){
        List<Object[]> list = new ArrayList<>();
        list.add( new Object[]{"FAAGUXBKSXBEIH-UHFFFAOYSA-N"," \n" +
                "  Marvin  04072016522D          \n" +
                "\n" +
                "  2  1  0  0  1  0            999 V2000\n" +
                "   -0.2063   -0.3572    0.0000 Ne  0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   -1.0313   -0.3572    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "M  END" });
        list.add( new Object[]{"JZLRUEPHIGLCNM-UHFFFAOYSA-N", " \n" +
                "  Marvin  04072016522D          \n" +
                "\n" +
                "  3  2  0  0  1  0            999 V2000\n" +
                "    1.5310    0.1768    0.0000 S   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "    0.8165   -0.2357    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "    0.1021    0.1768    0.0000 Ne  0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "  2  3  1  0  0  0  0\n" +
                "M  END"});

//        list.add( new Object(""))
        return list;
    }

    @Test
    public void assertInchiKeyCorrect() throws Exception{

        assertEquals( expectedInchi, Chemical.parseMol(mol).toInchi().getKey());
    }
}
