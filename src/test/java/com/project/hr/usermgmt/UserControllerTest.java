package com.project.hr.usermgmt;

//import static org.junit.Assert.assertArrayEquals;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	// bind the above RANDOM_PORT
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getUser() throws Exception {
		ResponseEntity<String> response = restTemplate
				.getForEntity(new URL("http://localhost:" + port + "/users/E0001").toString(), String.class);
		// assertEquals("Hello Controller", response.getBody());
	}

	@Test
	public void createUser() throws Exception {

		String payload = "{\"id\":\"E0001\",\"login\":\"hpotter\",\"name\":\"Harry Potter\",\"salary\":1234,\"startdate\":\"2001-11-16\"}";

		ResponseEntity<String> response = restTemplate
				.postForEntity(new URL("http://localhost:" + port + "/users").toString(), payload, String.class);
	}

	@Test
	public void updateUser() throws Exception {
		String payload = "{\"id\":\"E0001\",\"login\":\"hpotter\",\"name\":\"Harry Potter\",\"salary\":1234,\"startdate\":\"2001-11-16\"}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "E0001");

		restTemplate.put(new URL("http://localhost:" + port + "/users/E0001").toString(), payload, params);
	}

	@Test
	public void deleteUser() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "E0001");
		restTemplate.delete(new URL("http://localhost:" + port + "/users/E0001").toString(), params);
	}

	@Test
	public void getUserByFilter() throws Exception {
		ResponseEntity<String> response = restTemplate
				.getForEntity(new URL("http://localhost:" + port + "/users/1000/4000").toString(), String.class);
	}

}
