package com.interview.onlineTest.controller;


import com.interview.onlineTest.repository.QueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    QueryRepository testingRepository;

    @WithMockUser(value = "admin", password = "1", roles = "ADMIN")
    @Test
    public void countUsers_get_login_ok() throws Exception {

        ResultActions response = mockMvc.perform(get("/countUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("8")));
    }

    @WithMockUser(value = "admin", password = "1", roles = "ADMIN")
    @Test
    public void countUsersTested_get_login_ok() throws Exception {

        ResultActions response = mockMvc.perform(get("/countUsersTested"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("7")));
    }

    @WithMockUser(value = "admin", password = "1", roles = "ADMIN")
    @Test
    public void countUsersTestedAllQuestions_get_login_ok() throws Exception {

        ResultActions response = mockMvc.perform(get("/countUsersTestedAllQuestions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("3")));
    }

    @WithMockUser(value = "admin", password = "1", roles = "ADMIN")
    @Test
    public void countUsersTestedAllQuestionsSuccess_get_login_ok() throws Exception {

        ResultActions response = mockMvc.perform(get("/countUsersTestedAllQuestionsSuccess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("2")));
    }

    @WithMockUser(value = "u", password = "1", roles = "USER")
    @Test
    public void countRightAnswers_get_login_ok() throws Exception {

        ResultActions response = mockMvc.perform(get("/countRightAnswers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Процент прохождения: 100.00%,  5 правильных ответов из 5")));
    }

    @WithMockUser(value = "u", password = "1", roles = "USER")
    @Test
    public void countUsersTestedWorseCurrent_get_login_ok() throws Exception {

        ResultActions response = mockMvc.perform(get("/countUsersTestedWorseCurrent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Процент пользователей: 71.43%,  5 пользователей из 7")));
    }

    @WithMockUser(value = "u", password = "1", roles = "USER")
    @Test
    public void countUsersTestedBetterCurrent_get_login_ok() throws Exception {

        ResultActions response = mockMvc.perform(get("/countUsersTestedBetterCurrent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Процент пользователей: 0.00%,  0 пользователей из 7")));
    }

    @WithMockUser(value = "u", password = "1", roles = "USER")
    @Test
    public void countUsersTestedEqualCurrent_get_login_ok() throws Exception {

        ResultActions response = mockMvc.perform(get("/countUsersTestedEqualCurrent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Процент пользователей: 14.29%,  1 пользователей из 7")));
    }
}
