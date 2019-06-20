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
