package com.interview.onlineTest.controller;


import com.interview.onlineTest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @WithMockUser(value = "admin", password = "1", roles ="ADMIN")
    @Test
    public void users_get_login_ok() throws Exception {

        mockMvc.perform(get("/users"))
             //   .andDo(print())
                .andExpect(status().isOk())
             //   .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("admin")))
                .andExpect(jsonPath("$[0].enabled", is(true)));
    }

    @Test
    public void users_post_login_ok() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON).content("{\"username\":\"u9\",\"password\":\"1\"}")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void users_get_nologin_401() throws Exception {
        mockMvc.perform(get("/users"))
             //   .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "u", password = "1", roles ="USER")
    @Test
    public void users_get_nologin_403() throws Exception {
        mockMvc.perform(get("/users"))
             //   .andDo(print())
                .andExpect(status().isForbidden());
    }

}
