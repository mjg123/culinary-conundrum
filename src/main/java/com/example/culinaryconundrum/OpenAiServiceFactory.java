package com.example.culinaryconundrum;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class OpenAiServiceFactory {

    private final OpenAiService openAiService;

    public OpenAiServiceFactory(@Value("${openai.api-token}") String openAiToken) {
        openAiService = new OpenAiService(openAiToken, Duration.ofSeconds(30));
    }

    @Bean
    public OpenAiService openAiService() {
        return openAiService;
    }
}
