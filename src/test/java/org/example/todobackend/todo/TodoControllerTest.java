package org.example.todobackend.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                                            [
                                            
                                            ]
                                            """));



    }


    //ControllerTest-Integrationstest
    @Test
    void postTodo() throws Exception {

        //GIVEN

        //WHEN
        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        
                        {
                            "description": "test-description",
                            "status": "OPEN"
                        }
                        """)
                )
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "description": "test-description",
                        "status": "OPEN"
                        }
                        """))

                .andExpect(jsonPath("$.id").isNotEmpty());






    }
}