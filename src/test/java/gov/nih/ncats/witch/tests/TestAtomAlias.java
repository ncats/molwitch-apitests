package gov.nih.ncats.witch.tests;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import gov.nih.ncats.witch.Atom;
import gov.nih.ncats.witch.Chemical;

public class TestAtomAlias {

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
