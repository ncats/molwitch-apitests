package gov.nih.ncats.witch.tests;

import java.io.IOException;

import org.junit.Test;

import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.ChemicalBuilder;
public class ProblematicSmiles {
//Cl.COc4ccc5nc1c(CC[N@]3C[C@H]2C[C@@H]1[C@@H]3[C@H](C2)CC)c5c4    NCGC00247731-01
	
	@Test
	public void parse5memberRingWithN() throws IOException{
		//CDK throws an exception with this lowercase n
		//the Chemkit wrapper will replace it with [nH] if it fails in CDK only
		Chemical.createFromSmilesAndComputeCoordinates("COc4ccc5nc1c(CC[N@]3C[C@H]2C[C@@H]1[C@@H]3[C@H](C2)CC)c5c4");
	}
	
	@Test
	public void fiveMemberRing() throws IOException{
		Chemical.createFromSmiles("CC(C)N1CCN(CC1)C(=O)[C@H](Cc2ccc3nc(=O)oc3c2)NC(=O)N4CC[C@@]5(CC4)NC(=O)Nc6ccccc56");
	}
	
	@Test
	public void fiveMemberRingBuilder() throws IOException{
		ChemicalBuilder.createFromSmiles("CC(C)N1CCN(CC1)C(=O)[C@H](Cc2ccc3nc(=O)oc3c2)NC(=O)N4CC[C@@]5(CC4)NC(=O)Nc6ccccc56")
				.build();
	}
}
