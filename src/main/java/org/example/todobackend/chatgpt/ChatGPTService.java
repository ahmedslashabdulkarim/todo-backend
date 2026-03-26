package org.example.todobackend.chatgpt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
@Service
public class ChatGPTService {

    private final RestTemplate restTemplate;
    private final String chatGptUrl;
    private final String apiKey;

    public ChatGPTService(RestTemplate restTemplate,
                          @Value("${chatgpt.url}") String chatGptUrl,
                          @Value("${chatgpt.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.chatGptUrl = chatGptUrl;
        this.apiKey = apiKey;
    }

    public String checkSpelling(String text) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", "Korrigiere diesen Text: " + text
                        )
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ChatGptResponse response = restTemplate
                .postForEntity(chatGptUrl, request, ChatGptResponse.class)
                .getBody();

        return response.choices().get(0).message().content();
    }
}

