package org.example.todobackend.todo;

import org.example.todobackend.chatgpt.ChatGPTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class TodoServiceChatGptIntegrationTest {

    RestTemplate restTemplate;
    MockRestServiceServer mockServer;
    ChatGPTService chatGPTService;

    TodoRepository todoRepository = mock(TodoRepository.class);
    IdService idService = mock(IdService.class);

    TodoService todoService;

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        chatGPTService = new ChatGPTService(
                restTemplate,
                "http://fake-chatgpt-url.com",
                "dummy-key"
        );

        todoService = new TodoService(todoRepository, idService, chatGPTService);
    }

    @Test
    void addTodo_usesChatGptToCorrectDescription() {
        // GIVEN
        String original = "Das ist ein tehst"; //der TodoService bekommt dies Text ungefiltert.
        String corrected = "Das ist ein Test"; // Text den ChatGPT zurückgeben

        mockServer.expect(requestTo("http://fake-chatgpt-url.com"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
                .andRespond(withSuccess("""
                        {
                          "choices": [
                            { "text": "Das ist ein Test" }
                          ]
                        }
                        """, MediaType.APPLICATION_JSON));

        when(idService.randomId()).thenReturn("123");

        Todo expected = new Todo("123", corrected, TodoStatus.OPEN);
        when(todoRepository.save(expected)).thenReturn(expected);

        // WHEN
        Todo actual = todoService.addTodo(new NewTodo(original, TodoStatus.OPEN));

        // THEN
        mockServer.verify();
        verify(todoRepository).save(expected);
    }
}
