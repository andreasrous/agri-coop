package gr.hua.agricoop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AgriCoopApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateUser() throws Exception {
		String userJson = "{\"username\":\"apiuser\",\"email\":\"api@hua.gr\",\"password\":\"pass123\"}";
		ResultActions result = mockMvc.perform(post("/api/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson));
		result.andExpect(status().isOk());
	}

	@Test
	public void testSignUser() throws Exception {
		String userJson = "{\"username\":\"apiuser\",\"password\":\"pass123\"}";
		ResultActions result = mockMvc.perform(post("/api/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson));
		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("apiuser"));
	}
}
