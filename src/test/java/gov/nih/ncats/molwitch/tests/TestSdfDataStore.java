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

import gov.nih.ncats.common.io.InputStreamSupplier;
import gov.nih.ncats.common.iter.CloseableIterator;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.datastores.ChemicalDataStore;
import gov.nih.ncats.molwitch.datastores.FileChemicalDataStore;
import gov.nih.ncats.molwitch.io.ChemicalReader;
import gov.nih.ncats.molwitch.io.ChemicalReaderFactory;
import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
public class TestSdfDataStore {

    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("SDF DataStore");

    private static ChemicalDataStore sut;

    @BeforeClass
    public static void createDataStore() throws IOException {
        sut = ChemicalDataStore.forURL(TestSdfDataStore.class.getResource("/molFiles/Compound_000000001_000025000.sdf.gz"),
                25_000);
    }

    @Test
    public void assertNumberOfRecords(){
        assertEquals(23233, sut.getSize());
    }

    @Test
    public void iterateChemicalMatchesReader() throws IOException{
        try(ChemicalReader reader = ChemicalReaderFactory.newReader(InputStreamSupplier.forResourse(TestSdfDataStore.class.getResource("/molFiles/Compound_000000001_000025000.sdf.gz")).get());
            CloseableIterator<Chemical> iter = sut.getIterator();
        ){
            int count=0;
            while(reader.canRead()){
                assertTrue(iter.hasNext());
                Chemical expected = reader.read();
                Chemical actual = iter.next();
                assertEquals(expected.toInchi().getKey(), actual.toInchi().getKey());
                count++;
            }
            assertFalse(iter.hasNext());
            assertEquals(23233, count);
        }
    }

    @Test
    public void randomAccess() throws IOException{
        Set<Integer> offsets = new HashSet<>(Arrays.asList(0, 40, 234, 767, 10_000, 11_000, 20_000, 23232));

        Map<Integer, Chemical> map = new HashMap<>();
        try(ChemicalReader reader = ChemicalReaderFactory.newReader(InputStreamSupplier.forResourse(TestSdfDataStore.class.getResource("/molFiles/Compound_000000001_000025000.sdf.gz")).get());
        ){
            int count=0;
            while(reader.canRead()){

                Chemical chem = reader.read();
                if(offsets.contains(count)){
                    map.put(count, chem);
                }
                count++;
            }
        }
        assertFalse(map.isEmpty());
        for(Map.Entry<Integer, Chemical> entry : map.entrySet()){
            assertEquals(entry.getValue().toInchi().getKey(), sut.get(entry.getKey()).toInchi().getKey());
        }

    }
}
