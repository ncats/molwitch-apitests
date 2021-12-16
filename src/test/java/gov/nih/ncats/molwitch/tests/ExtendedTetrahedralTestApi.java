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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Chemical;
import gov.nih.ncats.molwitch.Chirality;
import gov.nih.ncats.molwitch.ExtendedTetrahedralChirality;
import gov.nih.ncats.molwitch.internal.InternalUtil;
import gov.nih.ncats.molwitch.Bond.BondType;
import gov.nih.ncats.molwitch.Bond.Stereo;

import static org.junit.Assert.*;

public class ExtendedTetrahedralTestApi {
    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Extended Tetrahedral");

    @Before
    public void toggleDebugOn() {
        InternalUtil.on();
    }
    
    @Before
    public void toggleDebugOff() {
        InternalUtil.off();
    }
    
    @Test
    public void hasAExtendedTetrahedralExplicitH() throws IOException{
        try(InputStream in= getClass().getResourceAsStream("/molFiles/ChEBI_38402.mol");
            
                ){
            Chemical c= Chemical.parseMol(in);
            c.makeHydrogensExplicit();
            c=Chemical.parseMol(c.toMol());
            System.out.println(c.toMol());
            
            
            List<ExtendedTetrahedralChirality> list = c.getExtendedTetrahedrals();
            
            assertEquals(1, list.size());
            //should look like this:
            
            //
            // C           H
            //  \         /
            //   C = C = C
            //  /         \
            // H            C
            //
            //chirality is counter clockwise starting at 11 oclock
            
            ExtendedTetrahedralChirality extendedTetrahdral = list.get(0);
            
            assertEquals(Chirality.S, extendedTetrahdral.getChirality());
            
            assertEquals("C", extendedTetrahdral.getCenterAtom().getSymbol());
            assertEquals("C,C", extendedTetrahdral.getTerminalAtoms().stream()
                          .map(Atom::getSymbol)
                          .collect(Collectors.joining(",")));
            
            for(Atom a:  extendedTetrahdral.getTerminalAtoms()) {
                assertEquals( BondType.DOUBLE, a.bondTo(extendedTetrahdral.getCenterAtom()).get().getBondType());
            }
            
            List<Atom> peripheralAtoms = extendedTetrahdral.getPeripheralAtoms();
            assertEquals(4, peripheralAtoms.size());
            
        
            assertEquals( "C", peripheralAtoms.get(0).getSymbol());
            assertEquals( "H", peripheralAtoms.get(1).getSymbol());
            assertEquals( "C", peripheralAtoms.get(2).getSymbol());
            assertEquals( "H", peripheralAtoms.get(3).getSymbol());
            
        }
    }
    
    
    @Test
    public void hasAExtendedTetrahedralExplicitHWithoutWedges() throws IOException{
        try(InputStream in= getClass().getResourceAsStream("/molFiles/ChEBI_38402.mol");
            
                ){
            Chemical c= Chemical.parseMol(in);
            
            c.makeHydrogensExplicit();
            
            c.bonds().filter(bb->{
                switch(bb.getStereo()) {
                case DOWN:
                case DOWN_INVERTED:
                case UP:
                case UP_INVERTED:
                    return true;
                default:
                    return false;

                }
            })
            .forEach(bb->bb.setStereo(Stereo.NONE));
            c=Chemical.parseMol(c.toMol());
            
            List<ExtendedTetrahedralChirality> list = c.getExtendedTetrahedrals();
            
            assertEquals(1, list.size());
            //should look like this:
            
            //
            // C           H
            //  \         /
            //   C = C = C
            //  /         \
            // H            C
            //
            //chirality is counter clockwise starting at 11 oclock
            
            ExtendedTetrahedralChirality extendedTetrahdral = list.get(0);
            
            assertEquals(Chirality.Parity_Either, extendedTetrahdral.getChirality());
            
            assertEquals("C", extendedTetrahdral.getCenterAtom().getSymbol());
            assertEquals("C,C", extendedTetrahdral.getTerminalAtoms().stream()
                          .map(Atom::getSymbol)
                          .collect(Collectors.joining(",")));
            
            for(Atom a:  extendedTetrahdral.getTerminalAtoms()) {
                assertEquals( BondType.DOUBLE, a.bondTo(extendedTetrahdral.getCenterAtom()).get().getBondType());
            }
            
            List<Atom> peripheralAtoms = extendedTetrahdral.getPeripheralAtoms();
            assertEquals(4, peripheralAtoms.size());
            
        
            assertEquals( "C", peripheralAtoms.get(0).getSymbol());
            assertEquals( "H", peripheralAtoms.get(1).getSymbol());
            assertEquals( "C", peripheralAtoms.get(2).getSymbol());
            assertEquals( "H", peripheralAtoms.get(3).getSymbol());
            
        }
    }
    
    

    @Test
    public void hasAExtendedTetrahedralWithRDesignation() throws IOException{
        
            Chemical c= Chemical.parseMol("\n"
                    + "   JSDraw212152114112D\n"
                    + "\n"
                    + "  7  6  0  0  1  0              0 V2000\n"
                    + "   19.5510   -8.2138    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   21.1110   -8.2160    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   22.6710   -8.2182    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   18.7729   -6.8617    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   18.7691   -9.5637    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   23.4529   -6.8683    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   23.4491   -9.5703    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "  1  2  2  0  0  0  0\n"
                    + "  2  3  2  0  0  0  0\n"
                    + "  1  4  1  0  0  0  0\n"
                    + "  1  5  1  0  0  0  0\n"
                    + "  3  6  1  6  0  0  0\n"
                    + "  3  7  1  1  0  0  0\n"
                    + "M  END");
            System.out.println(c.toMol());
            List<ExtendedTetrahedralChirality> list = c.getExtendedTetrahedrals();
            
            assertEquals(1, list.size());
            ExtendedTetrahedralChirality extendedTetrahdral = list.get(0);
            assertEquals(Chirality.R, extendedTetrahdral.getChirality());
    }
    

    @Test
    public void hasAExtendedTetrahedralWithSDesignation() throws IOException{
        
            Chemical c= Chemical.parseMol("\n"
                    + "   JSDraw212152114112D\n"
                    + "\n"
                    + "  7  6  0  0  1  0              0 V2000\n"
                    + "   19.5510   -8.2138    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   21.1110   -8.2160    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   22.6710   -8.2182    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   18.7729   -6.8617    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   18.7691   -9.5637    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   23.4529   -6.8683    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   23.4491   -9.5703    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "  1  2  2  0  0  0  0\n"
                    + "  2  3  2  0  0  0  0\n"
                    + "  1  4  1  0  0  0  0\n"
                    + "  1  5  1  0  0  0  0\n"
                    + "  3  6  1  1  0  0  0\n"
                    + "  3  7  1  6  0  0  0\n"
                    + "M  END");
            
            List<ExtendedTetrahedralChirality> list = c.getExtendedTetrahedrals();
            
            assertEquals(1, list.size());
            ExtendedTetrahedralChirality extendedTetrahdral = list.get(0);
            assertEquals(Chirality.S, extendedTetrahdral.getChirality());
    }
    

    @Test
    public void hasAExtendedTetrahedralWithNoDesignation() throws IOException{
        
            Chemical c= Chemical.parseMol("\n"
                    + "   JSDraw212152114112D\n"
                    + "\n"
                    + "  7  6  0  0  1  0              0 V2000\n"
                    + "   19.5510   -8.2138    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   21.1110   -8.2160    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   22.6710   -8.2182    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   18.7729   -6.8617    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   18.7691   -9.5637    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   23.4529   -6.8683    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "   23.4491   -9.5703    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"
                    + "  1  2  2  0  0  0  0\n"
                    + "  2  3  2  0  0  0  0\n"
                    + "  1  4  1  0  0  0  0\n"
                    + "  1  5  1  0  0  0  0\n"
                    + "  3  6  1  0  0  0  0\n"
                    + "  3  7  1  0  0  0  0\n"
                    + "M  END");
            
            List<ExtendedTetrahedralChirality> list = c.getExtendedTetrahedrals();
            
            assertEquals(1, list.size());
            ExtendedTetrahedralChirality extendedTetrahdral = list.get(0);
            assertEquals(Chirality.Parity_Either, extendedTetrahdral.getChirality());
    }
    
    @Test
    public void hasAExtendedTetrahedralImplicitH() throws IOException{
        try(InputStream in= getClass().getResourceAsStream("/molFiles/ChEBI_38402.mol");
            
                ){
            Chemical c= Chemical.parseMol(in);
            
            List<ExtendedTetrahedralChirality> list = c.getExtendedTetrahedrals();
            
            assertEquals(1, list.size());
            //should look like this:
            
            //
            // C           H
            //  \         /
            //   C = C = C
            //  /         \
            // H            C
            //
            //chirality is counter clockwise starting at 11 oclock
            
            
            //when its implicit H, the peripheralAtoms will have the terminal C's instead
            //when needed
            
            ExtendedTetrahedralChirality extendedTetrahdral = list.get(0);
            
            assertEquals(Chirality.S, extendedTetrahdral.getChirality());
            
            assertEquals("C", extendedTetrahdral.getCenterAtom().getSymbol());
            assertEquals("C,C", extendedTetrahdral.getTerminalAtoms().stream()
                          .map(Atom::getSymbol)
                          .collect(Collectors.joining(",")));
            
            for(Atom a:  extendedTetrahdral.getTerminalAtoms()) {
                assertEquals( BondType.DOUBLE, a.bondTo(extendedTetrahdral.getCenterAtom()).get().getBondType());
            }
            
            List<Atom> peripheralAtoms = extendedTetrahdral.getPeripheralAtoms();
            assertEquals(4, peripheralAtoms.size());

            
            assertEquals( "C", peripheralAtoms.get(0).getSymbol());
            assertEquals( "H", peripheralAtoms.get(1).getSymbol());
            assertEquals( "C", peripheralAtoms.get(2).getSymbol());
            assertEquals( "C", peripheralAtoms.get(3).getSymbol());
            
            assertEquals(peripheralAtoms.get(3), extendedTetrahdral.getTerminalAtoms().get(1));
            assertEquals(1, peripheralAtoms.get(3).getImplicitHCount());
            
            
        }
    }
}