package org.bliden.shakespeare.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.sun.jersey.api.client.Client;

public class TestResource {
	public static final String BASE_URI = "http://localhost:8080/api/";
	
	@Test
	public void testSpeaker() {
		Client client = Client.create();
		List<?> s = client.resource(BASE_URI + "speakers")
				.accept(MediaType.APPLICATION_JSON)
				.get(ArrayList.class);
		
		assertNotNull("should not be null", s );
		
		List<String> expected = new ArrayList<>();
		expected.add("Mercutio");
		expected.add("Juliet");
		
		assertEquals("should return a list", expected, s);
	}
}
