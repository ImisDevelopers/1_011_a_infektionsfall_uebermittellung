package de.coronavirus.imis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.coronavirus.imis.api.dto.AuthRequestDTO;
import de.coronavirus.imis.api.dto.TokenDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;

	@Test
	public void testAuthControllerForbidden() throws Exception {
		var dto = new AuthRequestDTO();
		dto.setPassword("test");
		dto.setUsername("test");

		mockMvc.perform(post("/api/auth")
				.content(mapper.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL)).andExpect(status().isForbidden());
	}

	@Test
	public void testAuthControllerAllowed() throws Exception {
		var dto = getTokenForUser("test_lab", "asdf");
		Assertions.assertThat(dto.getJwtToken()).isNotBlank();
	}

	@Test
	public void testPatientControllerForbidden() throws Exception {
		final ResultActions resultActions = mockMvc.perform(get("/api/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL)).andExpect(status().isForbidden());
	}

	@Test
	public void testGetAllPatientWithWrongRole() throws Exception {
		TokenDTO token = getTokenForUser("test_lab", "asdf");
		mockMvc.perform(get("/api/patients")
				.header("Bearer", token.getJwtToken())
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	public void testGetAllPatientValidCredentials() throws Exception {
		TokenDTO tokenDTO = getTokenForUser("test_doctor", "asdf");
		mockMvc.perform(get("/api/patients")
				.accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + tokenDTO.getJwtToken()))
				.andExpect(status().is2xxSuccessful());
	}

	private TokenDTO getTokenForUser(String username, String password) throws Exception {
		var dto = new AuthRequestDTO();
		dto.setPassword(password);
		dto.setUsername(username);

		var result = mockMvc.perform(post("/api/auth")
				.content(mapper.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL)).andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.jwtToken").isNotEmpty())
				.andReturn().getResponse().getContentAsString();
		return mapper.readValue(result, TokenDTO.class);
	}


}
