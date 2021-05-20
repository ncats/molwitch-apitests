/*
 * NCATS-WITCH-APITESTS
 *
 * Copyright 2021 NIH/NCATS
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

package gov.nih.ncats.molwitch.tests.isotopes;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.tests.contract.ApiContract;
import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.*;

public class ParseMolWithUncommonIsotopes {
    @ClassRule
    @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Isotopes");

    @Test
    @ApiContract(message = "TECHNETIUM TC-99M")
    public void TECHNETIUM_TC_99M() throws IOException {
        String mol = "\n" +
                "  Marvin  01132100482D          \n" +
                "\n" +
                "  1  0  0  0  0  0            999 V2000\n" +
                "    5.5917   -1.9000    0.0000 Tc  0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "M  ISO  1   1  99\n" +
                "M  END";

        Chemical c = Chemical.parseMol(mol);
        assertEquals(1, c.getAtomCount());
        Atom atom = c.getAtom(0);
        assertTrue(atom.isIsotope());
        assertEquals(99, atom.getMassNumber());


        String writtenMol = c.toMol();
        assertTrue(writtenMol, writtenMol.contains("ISO  1   1  99"));
    }

    @Test
    @ApiContract
    public void noIsotopes() throws IOException {
        Chemical c = Chemical.createFromSmiles("c1ccccc1");
        assertEquals(6, c.getAtomCount());
        c.atoms().forEach(a->assertFalse(a.isIsotope()));
    }

    @Test
    @ApiContract(message = "C14")
    public void c14Mol() throws IOException{
        String mol ="\n" +
                "  Marvin  01132110192D          \n" +
                "\n" +
                "  5  4  0  0  0  0            999 V2000\n" +
                "   15.2625   -4.0700    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   15.2625   -4.8950    0.0000 *   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   15.2625   -3.2450    0.0000 *   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   16.0875   -4.0700    0.0000 *   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.4375   -4.0700    0.0000 *   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "  1  3  1  0  0  0  0\n" +
                "  1  4  1  0  0  0  0\n" +
                "  1  5  1  0  0  0  0\n" +
                "M  ISO  1   1  14\n" +
                "M  END";

        Chemical c = Chemical.parseMol(mol);
        assertEquals(5, c.getAtomCount());
        Atom atom = c.getAtom(0);
        assertTrue(atom.isIsotope());
        assertEquals(14, atom.getMassNumber());

        String writtenMol = c.toMol();
        assertTrue(writtenMol, writtenMol.contains("ISO  1   1  14"));
    }
}
