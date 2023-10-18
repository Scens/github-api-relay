package brzezinski.rafal.githubapirelay.controller;

import brzezinski.rafal.githubapirelay.dto.UserDTO;
import brzezinski.rafal.githubapirelay.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUser_returnsUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("testLogin");

        Mockito.when(userService.getUser("testLogin")).thenReturn(userDTO);

        this.mockMvc.perform(get("/users/testLogin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("testLogin"));
    }
}

