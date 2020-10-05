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

package gov.nih.ncats.molwitch.tests;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestValenceErrorMol {
    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Valence Error");

    @Test
    public void mol() throws Exception{
        String mol = "\n  Marvin  07172010452D          \n\n" +
                " 22 22  0  0  0  0            999 V2000\n" +
                "   13.8063   -3.8323    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   13.8141   -4.6371    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   13.0821   -3.4378    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.5098   -3.4741    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   13.0535   -5.0472    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   12.3422   -3.8244    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   12.3422   -4.6293    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.5098   -2.5811    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   15.1926   -3.8401    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   11.6128   -5.0472    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   11.6180   -3.4222    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.5176   -4.9823    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   17.3498   -3.4585    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   15.9168   -3.4661    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   16.6177   -3.8607    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   17.3498   -2.6330    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   18.0818   -3.8687    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.5322   -5.7897    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   18.2791   -2.3553    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   18.9307   -3.6506    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   11.6101   -5.8726    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   10.8928   -4.6433    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  2  1  2  0  0  0  0\n" +
                "  3  1  1  0  0  0  0\n" +
                "  4  1  1  0  0  0  0\n" +
                "  5  2  1  0  0  0  0\n" +
                " 12  2  1  0  0  0  0\n" +
                "  6  3  2  0  0  0  0\n" +
                "  8  4  2  0  0  0  0\n" +
                "  9  4  1  0  0  0  0\n" +
                "  5  7  2  0  0  0  0\n" +
                "  7  6  1  0  0  0  0\n" +
                " 11  6  1  0  0  0  0\n" +
                " 10  7  1  0  0  0  0\n" +
                " 14  9  1  0  0  0  0\n" +
                " 10 21  2  0  0  0  0\n" +
                " 10 22  1  0  0  0  0\n" +
                " 18 12  1  0  0  0  0\n" +
                " 13 15  1  0  0  0  0\n" +
                " 16 13  1  0  0  0  0\n" +
                " 17 13  1  0  0  0  0\n" +
                " 15 14  1  0  0  0  0\n" +
                " 19 16  1  0  0  0  0\n" +
                " 20 17  1  0  0  0  0\n" +
                "M  END";

        Chemical c = Chemical.parse(mol);
        boolean actualHasError = c.atoms().anyMatch(Atom::hasValenceError);
        assertTrue(actualHasError);
    }
}
