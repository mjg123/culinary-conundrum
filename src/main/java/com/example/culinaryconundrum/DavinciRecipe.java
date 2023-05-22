package com.example.culinaryconundrum;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DavinciRecipe {

    private final TwilioSmsService smsService;
    private final OpenAiService openAiService;

    @Autowired
    public DavinciRecipe(TwilioSmsService smsService,
                         OpenAiService openAiService) {
        this.smsService = smsService;
        this.openAiService = openAiService;
    }

    @PostMapping("/davinci-recipe")
    @ResponseBody
    public void generateRecipe(
            @RequestParam("Body") String ingredients,
            @RequestParam("From") String userNumber,
            @RequestParam("To") String botNumber) {

        String prompt = "A recipe that uses " + ingredients + ":\n";

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .model("text-davinci-003")
                .echo(false)
                .maxTokens(200)
                .temperature(0.95)
                .n(1)
                .build();

        String recipe = openAiService.createCompletion(completionRequest)
                .getChoices().get(0).getText();

        smsService.send(userNumber, botNumber, recipe);
    }
}
