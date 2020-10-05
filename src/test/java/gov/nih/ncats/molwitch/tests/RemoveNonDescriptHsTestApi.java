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

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.ChemicalBuilder;
import gov.nih.ncats.molwitch.SGroup;
import gov.nih.ncats.molwitch.io.ChemicalReader;
import gov.nih.ncats.molwitch.io.ChemicalReaderFactory;
import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;
public class RemoveNonDescriptHsTestApi {

    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Remove Non Descript Hydrogens");

    @Test
    public void removeHsNoSgroups() throws IOException {
        try(ChemicalReader reader = ChemicalReaderFactory.newReader("sdf", getClass().getResourceAsStream("/withHs.sdf"))) {
            Chemical c = reader.read();
            assertTrue(c.atoms().filter(a-> a.getSymbol().equals("H")).findAny().isPresent());

            c.removeNonDescriptHydrogens();
            assertFalse(c.atoms().filter(a-> a.getSymbol().equals("H")).findAny().isPresent());

        }

    }
    @Test
    public void sgroupWithH() throws IOException {
        try(ChemicalReader reader = ChemicalReaderFactory.newReader("sdf", getClass().getResourceAsStream("/withHs.sdf"))) {
            Chemical c = reader.read();
            Atom h = c.atoms().filter(a-> a.getSymbol().equals("H")).findFirst().get();

            SGroup myGroup = c.addSGroup(SGroup.SGroupType.SUPERATOM_OR_ABBREVIATION);
            myGroup.addAtom(h);

            c.removeNonDescriptHydrogens();
            assertEquals(1, c.atoms().filter(a-> a.getSymbol().equals("H")).count());

        }

    }
    @Test
    public void sgroupConnectedToH() throws IOException {
        try(ChemicalReader reader = ChemicalReaderFactory.newReader("sdf", getClass().getResourceAsStream("/withHs.sdf"))) {
            Chemical c = reader.read();
            Atom h = c.atoms().filter(a-> a.getSymbol().equals("H")).findFirst().get();

            SGroup myGroup = c.addSGroup(SGroup.SGroupType.SUPERATOM_OR_ABBREVIATION);
            myGroup.addAtom(h.getBonds().get(0).getOtherAtom(h));

            c.removeNonDescriptHydrogens();
            assertEquals(1, c.atoms().filter(a-> a.getSymbol().equals("H")).count());

        }

    }

    @Test
    public void sGroupsWithoutHs() throws IOException{
        try(InputStream in = getClass().getResourceAsStream("/exampleSGroup.mol")) {
            Chemical c = Chemical.parseMol(in);
            assertTrue(c.hasSGroups());
            c.makeHydrogensExplicit();

            assertEquals(5, c.atoms().filter(a-> a.getSymbol().equals("H")).count());
            c.removeNonDescriptHydrogens();
            assertEquals(0, c.atoms().filter(a-> a.getSymbol().equals("H")).count());

        }
    }


    @Test
    public void deuterated() throws IOException{
//Deuterated chloroform
        Chemical c = Chemical.createFromSmiles("[2H]C(Cl)(Cl)Cl");
        assertEquals(1, c.atoms().filter(a-> a.getSymbol().equals("H")).count());
        c.removeNonDescriptHydrogens();
        assertEquals(1, c.atoms().filter(a-> a.getSymbol().equals("H")).count());

    }


}
