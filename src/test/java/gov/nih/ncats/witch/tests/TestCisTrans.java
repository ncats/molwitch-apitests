package gov.nih.ncats.witch.tests;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import gov.nih.ncats.witch.Bond;
import gov.nih.ncats.witch.Chemical;
import gov.nih.ncats.witch.Bond.DoubleBondStereo;

import static org.junit.Assert.*;
public class TestCisTrans {

	@Test
	public void getCisTrans() throws IOException{
		String resourceName = "/molFiles/testdoublebondconfig.mol";
		Chemical chem;
		try(InputStream in = getClass().getResourceAsStream(resourceName)){
			byte[] bytes = TestUtil.toByteArray(in);
			
			chem = Chemical.parseMol(bytes);
		}
		
		assertEquals(Bond.DoubleBondStereo.E_TRANS, chem.getBond(0).getDoubleBondStereo());
	}
}
