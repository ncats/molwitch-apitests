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

import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.search.MolSearcherFactory;
import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

public class MolSearcherTestApi {
    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("MolSearcher");

    @Test
    public void ensureIsobutaneSSSDoesntReturnIsoPentene() throws Exception{


        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209182020002D\n" +
                "\n" +
                "  4  3  0  0  0  0              0 V2000\n" +
                "   23.1921   -7.4013    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   23.6531   -8.8915    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1741   -9.2375    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.5929  -10.0359    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "  2  3  1  0  0  0  0\n" +
                "  2  4  1  0  0  0  0\n" +
                "M  END");

        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209182020002D\n" +
                "\n" +
                "  5  4  0  0  0  0              0 V2000\n" +
                "   23.1921   -7.4013    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   23.6531   -8.8915    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1741   -9.2375    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.5929  -10.0359    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   26.2344   -8.0932    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  2  0  0  0  0\n" +
                "  2  3  1  0  0  0  0\n" +
                "  2  4  1  0  0  0  0\n" +
                "  3  5  1  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

        assertFalse(hit.isPresent());
    }

    @Test
    public void ensureBasicSingleDoubleBondSearchDoesReturnSingleDoubleBondStructure() throws Exception{


        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162018192D\n" +
                "\n" +
                "  3  2  0  0  0  0            999 V2000\n" +
                "   18.0440   -9.4640    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   19.3950   -8.6840    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   20.7460   -9.4640    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "  2  3  2  0  0  0  0\n" +
                "M  END");

        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209162018202D\n" +
                "\n" +
                "  6  5  0  0  0  0            999 V2000\n" +
                "   19.1880  -14.0400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   17.8370  -13.2600    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   17.8370  -11.7000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   20.5390  -13.2600    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   20.5390  -11.7000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   19.1880  -10.9200    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  2  3  1  0  0  0  0\n" +
                "  1  4  1  0  0  0  0\n" +
                "  4  5  2  0  0  0  0\n" +
                "  5  6  1  0  0  0  0\n" +
                "  6  3  2  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

        assertTrue(hit.isPresent());
    }

    @Test
    public void ensureBasicBenzeneSearchWorksKekule() throws Exception{



        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162017232D\n" +
                "\n" +
                "  6  6  0  0  0  0            999 V2000\n" +
                "   26.4680  -12.3240    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1170  -11.5440    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1170   -9.9840    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8190  -11.5440    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8190   -9.9840    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   26.4680   -9.2040    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  2  0  0  0  0\n" +
                "  2  3  1  0  0  0  0\n" +
                "  1  4  1  0  0  0  0\n" +
                "  4  5  2  0  0  0  0\n" +
                "  5  6  1  0  0  0  0\n" +
                "  6  3  2  0  0  0  0\n" +
                "M  END");
        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209162017232D\n" +
                "\n" +
                "  6  6  0  0  0  0            999 V2000\n" +
                "   26.4680  -12.3240    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1170  -11.5440    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1170   -9.9840    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8190  -11.5440    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8190   -9.9840    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   26.4680   -9.2040    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  2  0  0  0  0\n" +
                "  2  3  1  0  0  0  0\n" +
                "  1  4  1  0  0  0  0\n" +
                "  4  5  2  0  0  0  0\n" +
                "  5  6  1  0  0  0  0\n" +
                "  6  3  2  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

        assertTrue(hit.isPresent());
    }

    @Test
    public void ensureBasicBenzeneSearchWorksAromatic() throws Exception{



        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162017232D\n" +
                "\n" +
                "  6  6  0  0  0  0            999 V2000\n" +
                "   26.4680  -12.3240    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1170  -11.5440    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1170   -9.9840    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8190  -11.5440    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8190   -9.9840    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   26.4680   -9.2040    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  4  0  0  0  0\n" +
                "  2  3  4  0  0  0  0\n" +
                "  1  4  4  0  0  0  0\n" +
                "  4  5  4  0  0  0  0\n" +
                "  5  6  4  0  0  0  0\n" +
                "  6  3  4  0  0  0  0\n" +
                "M  END");
        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209162017232D\n" +
                "\n" +
                "  6  6  0  0  0  0            999 V2000\n" +
                "   26.4680  -12.3240    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1170  -11.5440    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1170   -9.9840    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8190  -11.5440    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8190   -9.9840    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   26.4680   -9.2040    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  4  0  0  0  0\n" +
                "  2  3  4  0  0  0  0\n" +
                "  1  4  4  0  0  0  0\n" +
                "  4  5  4  0  0  0  0\n" +
                "  5  6  4  0  0  0  0\n" +
                "  6  3  4  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

        assertTrue(hit.isPresent());
    }

    @Test
    public void ensureBasicSSSCanGiveNegativeResult() throws Exception{


        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162010482D\n" +
                "\n" +
                "  1  0  0  0  0  0            999 V2000\n" +
                "   15.8080   -7.0436    0.0000 P   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "M  END");
        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209162010482D\n" +
                "\n" +
                "  1  0  0  0  0  0            999 V2000\n" +
                "   15.8080   -7.0436    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

        assertFalse(hit.isPresent());


    }

    @Test
    public void ensureBasicSSSCanGivePositiveResult() throws Exception{


        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162010482D\n" +
                "\n" +
                "  1  0  0  0  0  0            999 V2000\n" +
                "   15.8080   -7.0436    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "M  END");
        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209162010482D\n" +
                "\n" +
                "  1  0  0  0  0  0            999 V2000\n" +
                "   15.8080   -7.0436    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

        assertTrue(hit.isPresent());


    }

    @Test
    public void ensureBasicSSSCanGivePositiveResultOnStrictSubstructure() throws Exception{


        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162010482D\n" +
                "\n" +
                "  1  0  0  0  0  0            999 V2000\n" +
                "   15.8080   -7.0436    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "M  END");
        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209162016432D\n" +
                "\n" +
                "  2  0  0  0  0  0            999 V2000\n" +
                "   19.0840   -8.3720    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.7760   -9.9320    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

        assertTrue(hit.isPresent());


    }

    @Test
    public void ensureBasicSSSCanGivePositiveResultOnStrictSubstructureConnected() throws Exception{


        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162010482D\n" +
                "\n" +
                "  1  0  0  0  0  0            999 V2000\n" +
                "   15.8080   -7.0436    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "M  END");
        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209162016482D\n" +
                "\n" +
                "  2  1  0  0  0  0            999 V2000\n" +
                "   19.0840   -8.3720    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.7760   -9.9320    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

        assertTrue(hit.isPresent());
    }

    @Test
    public void ensureBasicSSSCanGivePositiveResultOnStrictSubstructureConnectedQueryConnectedTarget() throws Exception{


        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162016482D\n" +
                "\n" +
                "  2  1  0  0  0  0            999 V2000\n" +
                "   19.0840   -8.3720    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.7760   -9.9320    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "M  END");
        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209162016482D\n" +
                "\n" +
                "  2  1  0  0  0  0            999 V2000\n" +
                "   19.0840   -8.3720    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.7760   -9.9320    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

       assertTrue(hit.isPresent());
    }

    @Test
    public void ensureBasicSSSCanGivePositiveResultOnStrictSubstructureDisconnectedQueryConnectedTarget() throws Exception{


        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162016482D\n" +
                "\n" +
                "  2  0  0  0  0  0            999 V2000\n" +
                "   19.0840   -8.3720    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.7760   -9.9320    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "M  END");
        Chemical t=Chemical.parseMol("\n" +
                "   JSDraw209162016482D\n" +
                "\n" +
                "  2  1  0  0  0  0            999 V2000\n" +
                "   19.0840   -8.3720    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.7760   -9.9320    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);

        assertTrue(hit.isPresent());
    }

    @Test
    public void ensureBasicSSSCanGiveNegativeResultOnCyclohexaneCase() throws Exception{


        Chemical p=Chemical.parseMol("\n" +
                "   JSDraw209162014542D\n" +
                "\n" +
                "  6  6  0  0  0  0              0 V2000\n" +
                "   23.1920   -9.2560    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   21.8410   -8.4760    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   21.8410   -6.9160    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   23.1920   -6.1360    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   24.5430   -6.9160    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   24.5430   -8.4760    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "  2  3  1  0  0  0  0\n" +
                "  3  4  1  0  0  0  0\n" +
                "  4  5  1  0  0  0  0\n" +
                "  5  6  1  0  0  0  0\n" +
                "  6  1  1  0  0  0  0\n" +
                "M  END");
        Chemical t=Chemical.parseMol("\n" +
                "  Symyx   08281518122D 1   1.00000     0.00000     0\n" +
                "\n" +
                " 27 29  0  0  1  0            999 V2000\n" +
                "    7.1375   -2.2042    0.0000 N   0  0  3  0  0  0           0  0  0\n" +
                "    6.0500   -1.8583    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "    6.0500   -0.7125    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "    7.1292   -0.3542    0.0000 N   0  0  0  0  0  0           0  0  0\n" +
                "    7.8083   -1.2792    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "    5.0042   -0.0375    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "    5.0042   -2.4667    0.0000 N   0  0  0  0  0  0           0  0  0\n" +
                "    3.9333   -0.6458    0.0000 N   0  0  0  0  0  0           0  0  0\n" +
                "    3.9333   -1.8583    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "    5.0042    1.1417    0.0000 N   0  0  0  0  0  0           0  0  0\n" +
                "    7.1042   -3.9750    0.0000 C   0  0  1  0  0  0           0  0  0\n" +
                "    6.7583   -5.0833    0.0000 C   0  0  1  0  0  0           0  0  0\n" +
                "    6.1333   -3.2625    0.0000 O   0  0  0  0  0  0           0  0  0\n" +
                "    5.1750   -3.9625    0.0000 C   0  0  1  0  0  0           0  0  0\n" +
                "    5.5583   -5.0833    0.0000 C   0  0  1  0  0  0           0  0  0\n" +
                "    4.1500   -3.3708    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "    3.1250   -3.9625    0.0000 S   0  3  2  0  0  0           0  0  0\n" +
                "    2.1000   -3.3708    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "    1.0750   -3.9625    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "    0.0500   -3.3708    0.0000 C   0  0  1  0  0  0           0  0  0\n" +
                "   -0.9750   -3.9625    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "   -2.0000   -3.3708    0.0000 O   0  5  0  0  0  0           0  0  0\n" +
                "    5.2458   -6.2250    0.0000 O   0  0  0  0  0  0           0  0  0\n" +
                "    7.3458   -6.1083    0.0000 O   0  0  0  0  0  0           0  0  0\n" +
                "    3.1208   -5.1417    0.0000 C   0  0  0  0  0  0           0  0  0\n" +
                "    0.0458   -2.1917    0.0000 N   0  0  0  0  0  0           0  0  0\n" +
                "   -0.9792   -5.1417    0.0000 O   0  0  0  0  0  0           0  0  0\n" +
                " 11  1  1  1     0  0\n" +
                " 12 11  1  0     0  0\n" +
                " 13 11  1  0     0  0\n" +
                " 14 13  1  0     0  0\n" +
                " 15 12  1  0     0  0\n" +
                " 15 14  1  0     0  0\n" +
                "  6  3  1  0     0  0\n" +
                " 14 16  1  1     0  0\n" +
                "  7  2  1  0     0  0\n" +
                " 16 17  1  0     0  0\n" +
                "  8  9  1  0     0  0\n" +
                " 17 18  1  0     0  0\n" +
                "  9  7  2  0     0  0\n" +
                " 18 19  1  0     0  0\n" +
                " 10  6  1  0     0  0\n" +
                " 19 20  1  0     0  0\n" +
                "  3  4  1  0     0  0\n" +
                " 20 21  1  0     0  0\n" +
                "  8  6  2  0     0  0\n" +
                " 21 22  1  0     0  0\n" +
                " 15 23  1  6     0  0\n" +
                "  2  1  1  0     0  0\n" +
                " 12 24  1  6     0  0\n" +
                "  3  2  2  0     0  0\n" +
                " 17 25  1  1     0  0\n" +
                "  4  5  2  0     0  0\n" +
                " 20 26  1  1     0  0\n" +
                "  5  1  1  0     0  0\n" +
                " 21 27  2  0     0  0\n" +
                "M  CHG  2  17   1  22  -1\n" +
                "M  END");



        Optional<int[]> hit = MolSearcherFactory.create(p).search(t);


        assertFalse(hit.isPresent());


    }
}
