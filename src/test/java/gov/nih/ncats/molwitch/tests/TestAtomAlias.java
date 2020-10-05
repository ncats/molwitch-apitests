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

import static org.junit.Assert.*;

import java.util.Optional;

import gov.nih.ncats.molwitch.tests.contract.BasicApiContractChecker;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.nih.ncats.molwitch.Atom;
import gov.nih.ncats.molwitch.Chemical;

public class TestAtomAlias {

    @ClassRule @Rule
    public static BasicApiContractChecker checker = new BasicApiContractChecker("Atom Alias");

    private Chemical chem;
	private Atom atom;
	@Before
	public void create() {
		chem = new Chemical();
		 atom = chem.addAtom("C");
	}
	@Test
	public void unsetAliasShouldBeEmpty() {

		assertEquals(Optional.empty(), atom.getAlias());
	}
	
	@Test
	public void setAliasShouldBeEmpty() {

		atom.setAlias("myAlias");
		assertEquals("myAlias", atom.getAlias().get());
	}
	
	@Test
	public void notRGroupWithAliasUnset() {
		assertFalse(atom.isRGroupAtom());
		assertTrue(!atom.getRGroupIndex().isPresent());
	}
	@Test
	public void notRGroupWithAliasSetToSomethingElse() {
		atom.setAlias("myAlias");
		assertFalse(atom.isRGroupAtom());
		assertTrue(!atom.getRGroupIndex().isPresent());
	}
	
	@Test
	public void isRGroupWithAliasSetToRXX() {
		atom.setAlias("R10");
		assertTrue(atom.isRGroupAtom());
		assertEquals(10, atom.getRGroupIndex().getAsInt());
	}
	
	@Test
	public void isRGroupWithRGroupSetDirectly() {
		atom.setRGroup(10);
		assertTrue(atom.isRGroupAtom());
		assertEquals(10, atom.getRGroupIndex().getAsInt());
	}
	
	@Test
	public void unsetAnAlreadySetRGroup() {
		atom.setRGroup(10);
		assertTrue(atom.isRGroupAtom());
		atom.setRGroup(null);
		assertFalse(atom.isRGroupAtom());
		assertTrue(!atom.getRGroupIndex().isPresent());
	}
	
	@Test
	public void unsetAnRGroupWithAnAliasAleadyExisting() {
		atom.setAlias("foo");
		atom.setRGroup(null);
		
		assertEquals("foo", atom.getAlias().get());
		assertFalse(atom.isRGroupAtom());
		assertTrue(!atom.getRGroupIndex().isPresent());
		
	}
	
	@Test
	public void changeRGroup() {
		atom.setRGroup(10);
		atom.setRGroup(5);
		
		assertTrue(atom.isRGroupAtom());
		assertEquals(5, atom.getRGroupIndex().getAsInt());
	}
}
