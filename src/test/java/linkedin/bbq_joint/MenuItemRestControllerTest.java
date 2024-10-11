package linkedin.bbq_joint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuItemRestController.class)
class MenuItemRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuItemRepository repository;


    @BeforeEach
    public void setUp() {
        when(repository
                .save(any(MenuItem.class)))
                .thenAnswer(answer -> answer.<MenuItem>getArgument(0));
    }


    @Test
    void post_ok() throws Exception {
        // arrange
        final var content = "{\"id\":\"00000000-0000-0000-0000-000000000002\",\"name\":\"Freddy's Lemonade\",\"price\":5,\"drink\":true}";
        final var resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // assert
        resultActions
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.CONTENT_TYPE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Freddy's Lemonade"))
                .andExpect(jsonPath("$.price").value("5"))
                .andExpect(jsonPath("$.drink").value("true"))
                .andExpect(jsonPath("$.id").value("00000000-0000-0000-0000-000000000002"));

        final var captor = ArgumentCaptor.forClass(MenuItem.class);
        verify(repository).save(captor.capture());
        assertNotNull(captor.getValue().getId());
    }


}