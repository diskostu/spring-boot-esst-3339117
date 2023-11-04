package linkedin.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(DirtySecretController.class)
public class DirtySecretControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldSaveSecrets() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.post("/dirty-secrets")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"name\": \"Linda\", \"secret\": \"Cheated her son into Stanford.\" }"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Linda"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
  }

}
