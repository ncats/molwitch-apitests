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

import gov.nih.ncats.molwitch.tests.contract.ApiContractSuiteRule;
import gov.nih.ncats.molwitch.tests.inchi.*;
import gov.nih.ncats.molwitch.tests.isotopes.ParseMolWithUncommonIsotopes;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        InchiTestApi.class,
        BrokenInchiTestApi.class,
        InchiGeneratorTestApi.class,
        StdInchiFromSdfTestApi.class,
        StdInChiKeysTestApi.class,

        EZTestApi.class,
        MolSearcherTestApi.class,
        ParseWeirdParityTestApi.class,
        RemoveNonDescriptHsTestApi.class,
        AtomAliasTestApi.class,
        AtomCoordsTestApi.class,
        ChemicalCloneTestApi.class,
        ChemicalSourceParsedCorrectlyTestApi.class,
        CisTransTestApi.class,
        CreateChemicalTestApi.class,
        DefaultFingerPrintTestApi.class,
        DfsTestApi.class,
        ExtendedTetrahedralTestApi.class,
        FingerPrintTestApi.class,
        MolParserTestApi.class,
        ProblematicSmilesTestApi.class,
        SdfDataStoreTestApi.class,
        TetrahedralsTestApi.class,
        UnknownChemicalStringInputTestApi.class,
        ValenceErrorTestApi.class,
        ValenceErrorMolTestApi.class,
        WriteAsMolFileTestApi.class,
        ParseMolWithUncommonIsotopes.class
})
public class ApiCheckerSuite {
    @ClassRule
    public static ApiContractSuiteRule checker = new ApiContractSuiteRule();
}
