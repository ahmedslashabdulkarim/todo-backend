package org.example.todobackend.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

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

    //ControllerTest-Integrationstest
    @Test
    @DirtiesContext
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


    @Test
    @DirtiesContext     //ControllerTest-Integrationstest
    void putTodo() throws Exception {
        //GIVEN
        Todo existingTodo = new Todo("1", "test-description", TodoStatus.OPEN);
        todoRepository.save(existingTodo);
        //WHEN
        mockMvc.perform(put("/api/todo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "description": "test-description-2",
                                "status": "IN_PROGRESS"
                            }
                        """))
        //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                        "id": "1",
                        "description": "test-description-2",
                        "status": "IN_PROGRESS"
                    }
                    """));
    }


    @Test
    @DirtiesContext     //ControllerTest-Integrationstest
    void getById() throws Exception {

        //GIVEN
        Todo existingTodo = new Todo("1", "test-description", TodoStatus.OPEN);
        todoRepository.save(existingTodo);

        //WHEN
        mockMvc.perform(get("/api/todo/1"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {
                                "id": "1",
                                "description": "test-description",
                                "status": "OPEN"
                            }
                        """));
        //THEN
    }

    @Test
    @DirtiesContext     //ControllerTest-Integrationstest
    void getByIdTest_whenInvalidId_thenStatus404() throws Exception {
        //GIVEN
        //WHEN
        mockMvc.perform(get("/api/todo/1"))
                //THEN
                .andExpect(status().isNotFound());
    }


    @Test
    @DirtiesContext     //ControllerTest-Integrationstest
    void deleteTodoById() throws Exception {
        //GIVEN
        Todo existingTodo = new Todo("1", "test-description", TodoStatus.OPEN);
        todoRepository.save(existingTodo);
        //WHEN
        mockMvc.perform(delete("/api/todo/1"))
        //THEN
                .andExpect(status().isOk());

    }


    //Global Excption
    @Test
    @DirtiesContext
    void getById_whenInvalidId_thenReturnErrorMessage() throws Exception {
        // WHEN
        mockMvc.perform(get("/api/todo/888"))
                // THEN
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Todo with id: 888 not found!"));
    }


}