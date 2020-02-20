package com.interview.onlineTest.controller;


import com.interview.onlineTest.repository.QueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    QueryRepository testingRepository;

    @WithMockUser(value = "u6", password = "1", roles = "USER")
    @Test
    public void answers_get_login_ok() throws Exception {

        String expectedResponse = "[{\"queryId\":0,\"text\":\"У кого есть хобот? ответ в им. падеже\",\"answer\":\"жевый\"},{\"queryId\":0,\"text\":\"Сколько часов в сутках? написать число\",\"answer\":\"aasas\"}]";
        ResultActions response = mockMvc.perform(get("/answers"))
               // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void answers_get_nologin_401() throws Exception {
        mockMvc.perform(get("/answers"))
               // .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "u3", password = "1", roles ="USER")
    @Test
    public void answers_post_login_ok_wrong_answers() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/answers")
                .accept(MediaType.APPLICATION_JSON).content("[{\"queryId\":\"1\",\"answer\":\"оранжевый\"},{\"queryId\":\"2\",\"answer\":\"12\"},{\"queryId\":\"4\",\"answer\":\"\"}]")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("$.message", is("Test passed. Number of correct answers: 1")))
                .andExpect(status().isOk());
    }

    @Test
    public void answers_post_nologin_401() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/answers").accept(MediaType.APPLICATION_JSON).content("{}")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());
    }

}