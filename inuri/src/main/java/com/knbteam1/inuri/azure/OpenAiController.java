package com.knbteam1.inuri.azure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OpenAiController {
    private final OpenAiService openAiService;

    public OpenAiController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateImage(@RequestBody String prompt) {
        System.out.println(prompt);
        String imageUrl = openAiService.generateImage(prompt);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);

        System.out.println("response = " + response);

        return ResponseEntity.ok(response);
    }
}
