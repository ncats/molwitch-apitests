/*
 * NCATS-WITCH-APITESTS
 *
 * Copyright 2022 NIH/NCATS
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

import gov.nih.ncats.molwitch.inchi.InChiResult;
import gov.nih.ncats.molwitch.inchi.Inchi;
import gov.nih.ncats.molwitch.inchi.InchiKey;
import gov.nih.ncats.molwitch.tests.contract.ApiContract;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class SameInchiKeyPrefixTestApi {
    @ApiContract(category = "Inchi from Mol")
    @Test
    public void sameBackbone() throws IOException {
        String mol1 = "8-Azabicyclo[3.2.1]octane-2-carboxylic acid, 3-(4-chlorophenyl)-8-methyl-, ph...\n" +
                "   JSDraw203232112492D\n" +
                "\n" +
                " 25 28  0  0  1  0              0 V2000\n" +
                "   25.3327   -7.1858    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.5652   -5.6432    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.0174   -5.0731    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.2499   -3.5306    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   28.7021   -2.9606    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   29.9217   -3.9333    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   29.6892   -5.4759    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   28.2370   -6.0459    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   26.5523   -8.1584    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   23.8805   -7.7557    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   23.7640   -9.3113    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.4750  -10.1901    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   20.9843   -9.7303    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.7369   -8.7184    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   22.7369   -6.6946    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   21.1944   -6.9270    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   20.4143   -8.2781    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   23.4498  -10.4974    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1149  -10.0913    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   25.1149  -11.6513    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   26.4660  -12.4314    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8170  -11.6513    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   27.8170  -10.0913    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   26.4660   -9.3113    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   29.1679  -12.4314    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "  2  3  1  0  0  0  0\n" +
                "  3  4  2  0  0  0  0\n" +
                "  4  5  1  0  0  0  0\n" +
                "  5  6  2  0  0  0  0\n" +
                "  7  6  1  0  0  0  0\n" +
                "  8  7  2  0  0  0  0\n" +
                "  3  8  1  0  0  0  0\n" +
                "  1  9  2  0  0  0  0\n" +
                " 10  1  1  1  0  0  0\n" +
                " 10 11  1  0  0  0  0\n" +
                " 11 12  1  0  0  0  0\n" +
                " 12 13  1  0  0  0  0\n" +
                " 13 14  1  1  0  0  0\n" +
                " 15 14  1  1  0  0  0\n" +
                " 10 15  1  0  0  0  0\n" +
                " 15 16  1  0  0  0  0\n" +
                " 16 17  1  0  0  0  0\n" +
                " 13 17  1  0  0  0  0\n" +
                " 14 18  1  0  0  0  0\n" +
                " 11 19  1  1  0  0  0\n" +
                " 19 20  2  0  0  0  0\n" +
                " 20 21  1  0  0  0  0\n" +
                " 21 22  2  0  0  0  0\n" +
                " 23 22  1  0  0  0  0\n" +
                " 24 23  2  0  0  0  0\n" +
                " 19 24  1  0  0  0  0\n" +
                " 22 25  1  0  0  0  0\n" +
                "M  END\n";

        String mol2 =
                "8-Azabicyclo[3.2.1]octane-2-carboxylic acid, 3-(4-chlorophenyl)-8-methyl-, ph...\n" +
                "  ACCLDraw08242113222D\n" +
                "\n" +
                " 25 28  0  0  1  0  0  0  0  0999 V2000\n" +
                "   12.9077   -8.9903    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   13.1402   -7.4477    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.5924   -6.8776    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.8249   -5.3351    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   16.2771   -4.7651    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   17.4967   -5.7378    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   17.2642   -7.2804    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   15.8120   -7.8504    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.1273   -9.9629    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   11.4555   -9.5602    0.0000 C   0  0  1  0  0  0  0  0  0  0  0  0\n" +
                "   11.3390  -11.1158    0.0000 C   0  0  1  0  0  0  0  0  0  0  0  0\n" +
                "   10.0500  -11.9946    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "    8.5593  -11.5348    0.0000 C   0  0  1  0  0  0  0  0  0  0  0  0\n" +
                "   10.3119  -10.5229    0.0000 N   0  0  3  0  0  0  0  0  0  0  0  0\n" +
                "   10.3119   -8.4991    0.0000 C   0  0  2  0  0  0  0  0  0  0  0  0\n" +
                "    8.7694   -8.7315    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "    7.9893  -10.0826    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   11.0248  -12.3019    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   12.6899  -11.8958    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   12.6899  -13.4558    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.0410  -14.2359    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   15.3920  -13.4558    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   15.3920  -11.8958    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   14.0410  -11.1158    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "   16.7429  -14.2359    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\n" +
                "  1  2  1  0  0  0  0\n" +
                "  2  3  1  0  0  0  0\n" +
                "  3  4  2  0  0  0  0\n" +
                "  4  5  1  0  0  0  0\n" +
                "  5  6  2  0  0  0  0\n" +
                "  7  6  1  0  0  0  0\n" +
                "  8  7  2  0  0  0  0\n" +
                "  3  8  1  0  0  0  0\n" +
                "  1  9  2  0  0  0  0\n" +
                " 10  1  1  1  0  0  0\n" +
                " 10 11  1  0  0  0  0\n" +
                " 11 12  1  0  0  0  0\n" +
                " 12 13  1  0  0  0  0\n" +
                " 13 14  1  1  0  0  0\n" +
                " 15 14  1  1  0  0  0\n" +
                " 10 15  1  0  0  0  0\n" +
                " 15 16  1  0  0  0  0\n" +
                " 16 17  1  0  0  0  0\n" +
                " 13 17  1  0  0  0  0\n" +
                " 14 18  1  0  0  0  0\n" +
                " 11 19  1  6  0  0  0\n" +
                " 19 20  2  0  0  0  0\n" +
                " 20 21  1  0  0  0  0\n" +
                " 21 22  2  0  0  0  0\n" +
                " 23 22  1  0  0  0  0\n" +
                " 24 23  2  0  0  0  0\n" +
                " 19 24  1  0  0  0  0\n" +
                " 22 25  1  0  0  0  0\n" +
                "M  END";

        InChiResult result1 = Inchi.computeInchiFromMol(mol1);
        InChiResult result2 = Inchi.computeInchiFromMol(mol2);
        InchiKey k1 = result1.getInchiKey().get();
        InchiKey k2 = result2.getInchiKey().get();

        assertTrue(k1.hasSameConnectivity(k2));
        assertFalse(k1.equals(k2));
    }
}
