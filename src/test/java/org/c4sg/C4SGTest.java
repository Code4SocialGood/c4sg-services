package org.c4sg;

import com.fasterxml.jackson.databind.ObjectMapper;

public class C4SGTest {

	String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	     }
	}
	
}