package gov.nih.ncats.molwitch.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.Chirality;
import gov.nih.ncats.molwitch.TetrahedralChirality;
import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;

public class HeteroAtomTetrahedralsTestApi {
    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Hetero Atom Tetrahedral");


    @Test
    public void tetrahedralPhosphorousIsReal() throws IOException{
        Chemical c = Chemical.parseMol("\n"
                + "   JSDraw212062112592D\n"
                + "\n"
                + "  8  7  0  0  0  0              0 V2000\n"
                + "   24.6113   -6.7064    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   23.8361   -8.0602    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2761   -8.0658    0.0000 P   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2817   -9.6258    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2705   -6.5058    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   20.7161   -8.0714    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9411   -9.4251    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9313   -6.7233    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "  1  2  1  0  0  0  0\n"
                + "  2  3  1  0  0  0  0\n"
                + "  3  4  1  0  0  0  0\n"
                + "  3  5  2  0  0  0  0\n"
                + "  3  6  1  0  0  0  0\n"
                + "  6  7  1  0  0  0  0\n"
                + "  6  8  1  0  0  0  0\n"
                + "M  END");

        List<TetrahedralChirality> tetrahedralChiralityList = c.getTetrahedrals();
        tetrahedralChiralityList.stream().forEach(System.out::println);
        assertEquals(1, tetrahedralChiralityList.size());
        Set<String> expected = tetrahedralChiralityList.stream().map(tet -> tet.getCenterAtom().getSymbol()).collect(Collectors.toSet());
        assertEquals(Collections.singleton("P"), expected);
    }


    @Test
    public void tetrahedralPhosphorousChargeSeparatedIsReal() throws IOException{
        Chemical c = Chemical.parseMol("\n"
                + "   JSDraw212062113002D\n"
                + "\n"
                + "  8  7  0  0  0  0              0 V2000\n"
                + "   24.6113   -6.7064    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   23.8361   -8.0602    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2761   -8.0658    0.0000 P   0  3  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2817   -9.6258    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2705   -6.5058    0.0000 O   0  5  0  0  0  0  0  0  0  0  0  0\n"
                + "   20.7161   -8.0714    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9411   -9.4251    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9313   -6.7233    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "  1  2  1  0  0  0  0\n"
                + "  2  3  1  0  0  0  0\n"
                + "  3  4  1  0  0  0  0\n"
                + "  3  5  1  0  0  0  0\n"
                + "  3  6  1  0  0  0  0\n"
                + "  6  7  1  0  0  0  0\n"
                + "  6  8  1  0  0  0  0\n"
                + "M  CHG  2   3   1   5  -1\n"
                + "M  END");

        List<TetrahedralChirality> tetrahedralChiralityList = c.getTetrahedrals();
        tetrahedralChiralityList.stream().forEach(System.out::println);
        assertEquals(1, tetrahedralChiralityList.size());
        Set<String> expected = tetrahedralChiralityList.stream().map(tet -> tet.getCenterAtom().getSymbol()).collect(Collectors.toSet());
        assertEquals(Collections.singleton("P"), expected);
    }



    @Test
    public void tetrahedralPhosphorousInPhosphateNotReal() throws IOException{
        Chemical c = Chemical.parseMol("\n"
                + "   JSDraw212062113002D\n"
                + "\n"
                + "  8  7  0  0  0  0              0 V2000\n"
                + "   24.6113   -6.7064    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   23.8361   -8.0602    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2761   -8.0658    0.0000 P   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2817   -9.6258    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2705   -6.5058    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   20.7161   -8.0714    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9411   -9.4251    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9313   -6.7233    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "  1  2  1  0  0  0  0\n"
                + "  2  3  1  0  0  0  0\n"
                + "  3  4  1  0  0  0  0\n"
                + "  3  5  2  0  0  0  0\n"
                + "  3  6  1  0  0  0  0\n"
                + "  6  7  1  0  0  0  0\n"
                + "  6  8  1  0  0  0  0\n"
                + "M  END");

        List<TetrahedralChirality> tetrahedralChiralityList = c.getTetrahedrals();
        tetrahedralChiralityList.stream().forEach(System.out::println);
        assertEquals(0, tetrahedralChiralityList.size());
    }


    @Test
    public void tetrahedralPhosphorousInPhosphateWithNegativeChargeNotReal() throws IOException{
        Chemical c = Chemical.parseMol("\n"
                + "   JSDraw212062113012D\n"
                + "\n"
                + "  8  7  0  0  0  0              0 V2000\n"
                + "   24.6113   -6.7064    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   23.8361   -8.0602    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2761   -8.0658    0.0000 P   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2817   -9.6258    0.0000 O   0  5  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2705   -6.5058    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   20.7161   -8.0714    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9411   -9.4251    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9313   -6.7233    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "  1  2  1  0  0  0  0\n"
                + "  2  3  1  0  0  0  0\n"
                + "  3  4  1  0  0  0  0\n"
                + "  3  5  2  0  0  0  0\n"
                + "  3  6  1  0  0  0  0\n"
                + "  6  7  1  0  0  0  0\n"
                + "  6  8  1  0  0  0  0\n"
                + "M  CHG  1   4  -1\n"
                + "M  END");

        List<TetrahedralChirality> tetrahedralChiralityList = c.getTetrahedrals();
        tetrahedralChiralityList.stream().forEach(System.out::println);
        assertEquals(0, tetrahedralChiralityList.size());
    }

    @Test
    public void tetrahedralPhosphorousInPhosphateWithMethoxyIsReal() throws IOException{
        Chemical c = Chemical.parseMol("\n"
                + "   JSDraw212062113022D\n"
                + "\n"
                + "  9  8  0  0  0  0              0 V2000\n"
                + "   24.6113   -6.7064    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   23.8361   -8.0602    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2761   -8.0658    0.0000 P   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2817   -9.6258    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2705   -6.5058    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   20.7161   -8.0714    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9411   -9.4251    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   19.9313   -6.7233    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   23.6327  -10.4058    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "  1  2  1  0  0  0  0\n"
                + "  2  3  1  0  0  0  0\n"
                + "  3  4  1  0  0  0  0\n"
                + "  3  5  2  0  0  0  0\n"
                + "  3  6  1  0  0  0  0\n"
                + "  6  7  1  0  0  0  0\n"
                + "  6  8  1  0  0  0  0\n"
                + "  4  9  1  0  0  0  0\n"
                + "M  END");

        List<TetrahedralChirality> tetrahedralChiralityList = c.getTetrahedrals();
        tetrahedralChiralityList.stream().forEach(System.out::println);
        assertEquals(1, tetrahedralChiralityList.size());
        Set<String> expected = tetrahedralChiralityList.stream().map(tet -> tet.getCenterAtom().getSymbol()).collect(Collectors.toSet());
        assertEquals(Collections.singleton("P"), expected);
    }

    

    @Test
    public void tetrahedralChiralPhosphorousInPhosphateWithMethoxyExpectingSisS() throws IOException{
        Chemical c = Chemical.parseMol("\n"
                + "   JSDraw212062113072D\n"
                + "\n"
                + "  9  8  0  0  1  0            999 V2000\n"
                + "   26.8840   -5.9460    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   26.1090   -7.3000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   24.5490   -7.3060    0.0000 P   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   24.5550   -8.8660    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   24.5440   -5.7460    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.9890   -7.3110    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2140   -8.6650    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   22.2040   -5.9630    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "   25.9060   -9.6460    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                + "  1  2  1  0  0  0  0\n"
                + "  2  3  1  0  0  0  0\n"
                + "  3  4  1  1  0  0  0\n"
                + "  3  5  2  0  0  0  0\n"
                + "  3  6  1  0  0  0  0\n"
                + "  6  7  1  0  0  0  0\n"
                + "  6  8  1  0  0  0  0\n"
                + "  4  9  1  0  0  0  0\n"
                + "M  END");

        List<TetrahedralChirality> tetrahedralChiralityList = c.getTetrahedrals();
        tetrahedralChiralityList.stream().forEach(System.out::println);
        assertEquals(1, tetrahedralChiralityList.size());
        Set<String> expected = tetrahedralChiralityList.stream().map(tet -> tet.getCenterAtom().getSymbol()).collect(Collectors.toSet());
        assertEquals(Collections.singleton("P"), expected);
        
        assertEquals(tetrahedralChiralityList.get(0).getChirality(),Chirality.S);
    }
}
