package com.interview.onlineTest.controller;

import com.interview.onlineTest.repository.QueryRepository;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    QueryRepository queryRepository;

    @WithMockUser(value = "u", password = "1", roles ="USER")
    @Test
    public void queries_get_login_ok() throws Exception {

        String expectedResponse = "[{\"id\":1,\"text\":\"Какого цвета апельсин?\",\"type\":1,\"right_answer\":0,\"answers\":[\"красный\",\"оранжевый\",\"зеленый\",\"синий\"]},{\"id\":2,\"text\":\"У кого есть хобот? ответ в им. падеже\",\"type\":2,\"right_answer\":0,\"answers\":[]},{\"id\":3,\"text\":\"Что такое yandex.ru?\",\"type\":1,\"right_answer\":0,\"answers\":[\"поисковик\",\"машина\",\"утюг\",\"утюг2\"]},{\"id\":4,\"text\":\"Сколько часов в сутках? написать число\",\"type\":2,\"right_answer\":0,\"answers\":[]},{\"id\":5,\"text\":\"Из чего делают творог?\",\"type\":1,\"right_answer\":0,\"answers\":[\"мясо\",\"мука\",\"квас\",\"молоко\"]}]";
        ResultActions response = mockMvc.perform(get("/queries"))
            //    .andDo(print())
                .andExpect(status().isOk())
         //       .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void queries_get_nologin_401() throws Exception {
        mockMvc.perform(get("/queries"))
              //  .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "admin", password = "1", roles ="ADMIN")
    @Test
    public void queries_post_login_ok() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/queries")
                .accept(MediaType.APPLICATION_JSON).content("{\"id\":1,\"text\":\"Какого цвета апельсин3\",\"type\":\"2\",\"right_answer\":\"0\",\"answers\":[\"3\"]}")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @WithMockUser(value = "admin", password = "1", roles ="ADMIN")
    @Test
    public void queries_post_login_ok_wrong_answers() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/queries")
                .accept(MediaType.APPLICATION_JSON).content("{\"id\":1,\"text\":\"Какого цвета апельсин3\",\"type\":\"2\",\"right_answer\":\"0\",\"answers\":[\"\"]}")
                .contentType(MediaType.APPLICATION_JSON);
       mockMvc.perform(requestBuilder)
              //  .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void queries_post_nologin_401() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/queries").accept(MediaType.APPLICATION_JSON).content("{}")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "u", password = "1", roles ="USER")
    @Test
    public void queries_post_wrong_login_403() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/queries").accept(MediaType.APPLICATION_JSON).content("{}")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
    }

}
