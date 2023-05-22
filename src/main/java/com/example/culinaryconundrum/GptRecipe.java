package com.example.culinaryconundrum;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GptRecipe {

    private final TwilioSmsService smsService;
    private final OpenAiService openAiService;

    @Autowired
    public GptRecipe(TwilioSmsService smsService,
                     OpenAiService openAiService) {
        this.smsService = smsService;
        this.openAiService = openAiService;
    }

    @PostMapping("/gpt-recipe")
    public void generateRecipe(
            @RequestParam("Body") String ingredients,
            @RequestParam("From") String userNumber,
            @RequestParam("To") String botNumber) {

        String prompt = "Tell me a recipe that uses " + ingredients;

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", prompt)))
                .model("gpt-3.5-turbo")
                .maxTokens(200)
                .temperature(0.95)
                .n(1)
                .build();

        String recipe = openAiService.createChatCompletion(chatCompletionRequest)
                .getChoices().get(0).getMessage().getContent();

        smsService.send(userNumber, botNumber, recipe);
    }
}
