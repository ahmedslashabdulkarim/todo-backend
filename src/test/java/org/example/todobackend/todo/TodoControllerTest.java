package org.example.todobackend.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void getAllTodos() throws Exception{
        //GIVEN

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))

        //THEN
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().json("""
                                            []
                                            """));



    }
}