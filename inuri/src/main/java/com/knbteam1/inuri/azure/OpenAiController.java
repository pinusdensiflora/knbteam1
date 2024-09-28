package com.knbteam1.inuri.azure;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class OpenAiController {
    private final OpenAiService openAiService;

    public OpenAiController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping("/generate")
    public String generateImage(@RequestBody String prompt) {
        System.out.println("prompt = " + prompt);
        String response =openAiService.generateImage(prompt);
        System.out.println("response = " + response);
        return response;
    }
}
