package com.knbteam1.inuri.azure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OpenAiService {

    @Value("${AZURE_RESOURCE_ENDPOINT}")
    private String AZURE_RESOURCE_ENDPOINT;
    @Value("${AZURE_RESOURCE_API_KEY}")
    private String AZURE_RESOURCE_API_KEY;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateImage(String prompt) {
        String apiVersion = "2024-02-15-preview";
        String url = AZURE_RESOURCE_ENDPOINT + "openai/deployments/dalle3/images/generations?api-version=" + apiVersion;

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", AZURE_RESOURCE_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("prompt", prompt);
        body.put("n", 1);
        body.put("size", "1024x1024");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null ) {
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> data = (List<Map<String, Object>>) responseBody.get("data");
            String imageUrl = (String) data.get(0).get("url");
            return imageUrl;
        }

        throw new RuntimeException("Error generating image" + response.getBody());
    }

}
