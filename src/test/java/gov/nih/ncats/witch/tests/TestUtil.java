package gov.nih.ncats.witch.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class TestUtil {

	public static final double DELTA = 0.001D;

	private TestUtil(){
		//can not instanitate
	}
	
	public static void assertEqualAndMatchingHashCode(Object a, Object b){
		assertEquals(a, b);
		assertEquals("hashcode", a.hashCode(), b.hashCode());
	}
	
	public static byte[] toByteArray(InputStream in) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[2048];
		
		int bytesRead;
		
		while ((bytesRead = in.read(buf)) >0){
			out.write(buf, 0, bytesRead);
		}
		
		return out.toByteArray();
	}



}
