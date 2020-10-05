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
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        InchiTest.class,
        TestBrokenInchi.class,
        TestInchiGenerator.class,
        TestStdInchiFromSdf.class,
        TestStdInChiKeys.class,

        EZTest.class,
        MolSearcherTest.class,
        ParseWeirdParityTest.class,
        RemoveNonDiescriptHsTest.class,
        TestAtomAlias.class,
        TestAtomCoords.class,
        TestChemicalClone.class,
        TestChemicalSourceParsedCorrectly.class,
        TestCisTrans.class,
        TestCreateChemical.class,
        TestDefaultFingerPrint.class,
        TestDfs.class,
        TestExtendedTetrahedral.class,
        TestFingerPrint.class,
        TestMolParser.class,
        TestProblematicSmiles.class,
        TestSdfDataStore.class,
        TestTetrahedrals.class,
        TestUnknownChemicalStringInput.class,
        TestValenceError.class,
        TestValenceErrorMol.class,
        TestWriteAsMolFile.class
})
public class ApiCheckerSuite {
    @ClassRule
    public static ApiContractSuiteRule checker = new ApiContractSuiteRule();
}
