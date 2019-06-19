package gov.nih.ncats.witch.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.Test;

import gov.nih.ncats.witch.Atom;
import gov.nih.ncats.witch.Bond;
import gov.nih.ncats.witch.BondTable;
import gov.nih.ncats.witch.Chemical;

import static org.junit.Assert.*;


public class TestDfs {

	@Test
	public void onePath() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates("CN=C=O");
		
		Atom n = chem.getAtom(1);
		assertEquals(7, n.getAtomicNumber());
		
		Atom o = chem.getAtom(3);
		assertEquals(8, o.getAtomicNumber());
		
		Atom middleC = chem.getAtom(2);
		assertEquals(6, middleC.getAtomicNumber());
		
		
		List<Bond> expected = new PathBuilder(chem)
							.start(n)
							.to(middleC)
							.to(o)
							.build();
		
		assertEquals(expected, chem.computeShortestPath(n, o));
		
		
	}
	@Test
	public void onePathAllPaths() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates("CN=C=O");
		
		Atom n = chem.getAtom(1);
		assertEquals(7, n.getAtomicNumber());
		
		Atom o = chem.getAtom(3);
		assertEquals(8, o.getAtomicNumber());
		
		Atom middleC = chem.getAtom(2);
		assertEquals(6, middleC.getAtomicNumber());
		
		
		List<Bond> expected = new PathBuilder(chem)
							.start(n)
							.to(middleC)
							.to(o)
							.build();
		
		assertEquals(Arrays.asList(expected), chem.computeAllPaths(n, o));
		
		
	}
	
	
	
	@Test
	public void shortestPathThroughRing() throws IOException{
		Chemical chem = Chemical.createFromSmilesAndComputeCoordinates("C(CC1=CC=CC=C1)NCC2=CC=CC=C2");
		
		Atom middleOfRing = chem.getAtom(3);
		
		Atom n = chem.getAtom(8);
		assertEquals(7, n.getAtomicNumber());

		
		List<Bond> expected = new PathBuilder(chem)
				.start(middleOfRing)
				.to(2)
				.to(1)
				.to(0)
				.to(n)
				.build();
		/*
		visitor.visit(new PathBuilder(chem)
							.start(middleOfRing)
							.to(4)
							.to(5)
							.to(6)
							.to(7)
							.to(2)
							.to(1)
							.to(0)
							.to(n)
							.build());
							*/
		
		assertEquals(expected, chem.computeShortestPath(middleOfRing, n));
		
	}
	private static class PathBuilder{
		
		List<Bond> expectedPath = new ArrayList<>();
		
		BondTable bondtable;
		Chemical chem;
		
		private int current;
		
		PathBuilder(Chemical chem){
			this.chem = chem;
			this.bondtable = chem.getBondTable();
		}
		
		PathBuilder start(Atom atom){
			Objects.requireNonNull(atom);
			current = chem.indexOf(atom);
			return this;
		}
		PathBuilder to(int index){
			return to(chem.getAtom(index));
		}
		PathBuilder to(Atom atom){
			Objects.requireNonNull(current);
			Objects.requireNonNull(atom);
			
			int other = chem.indexOf(atom);
			
			Bond bond = bondtable.getBond(current, other);
			if(bond ==null){
				throw new IllegalArgumentException("no bond between " + current + " and " + other);
			}
			expectedPath.add(bond);
			current = other;
			return this;
		}
		
		List<Bond> build(){
			return expectedPath;
				
		}
	}
}
