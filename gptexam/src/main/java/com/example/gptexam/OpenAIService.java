package com.example.gptexam;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
public class OpenAIService {

    private final WebClient webClient;

    // 생성자를 통해 API 키를 주입받고 WebClient를 설정합니다.
    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")  // OpenAI API URL 설정
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)  // API 키 설정
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")  // JSON 데이터로 요청
                .build();
    }

    // 대화 히스토리와 질문을 처리하는 GPT-4 모델 호출 메서드
    public Mono<String> getChatCompletion(String question, List<Map<String, String>> conversationHistory) {
        // 요청 바디 생성
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4");  // GPT-4 모델 사용

        // 대화 히스토리를 담을 messages 배열 생성
        JSONArray messages = new JSONArray();

        // 대화 히스토리가 있을 경우 이를 messages 배열에 추가
        if (conversationHistory != null && !conversationHistory.isEmpty()) {
            for (Map<String, String> message : conversationHistory) {
                JSONObject messageObject = new JSONObject();
                messageObject.put("role", message.get("role"));  // 역할 설정
                messageObject.put("content", message.get("content"));  // 대화 내용 추가
                messages.put(messageObject);  // messages 배열에 추가
            }
        }

        // 사용자 질문도 메시지 배열에 추가
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");  // 질문은 항상 사용자 역할로 설정
        userMessage.put("content", question);  // 질문 내용 설정
        messages.put(userMessage);  // 메시지 배열에 추가

        // 요청 바디에 messages 배열 추가
        requestBody.put("messages", messages);

        // WebClient를 사용하여 API 호출
        return webClient.post()
                .bodyValue(requestBody.toString())  // 요청 바디 전송
                .retrieve()  // 응답 받기
                .bodyToMono(String.class)  // 응답을 비동기로 받음
                .map(this::parseResponseBody)  // 응답 파싱
                .onErrorResume(WebClientResponseException.class, this::handleError);  // 에러 처리
    }

    // 응답 본문을 파싱하는 메서드
    private String parseResponseBody(String responseBody) {
        // JSON 파싱하여 응답 메시지 추출
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");

        // 응답이 유효한지 확인한 후 첫 번째 응답의 내용을 추출
        if (choices.length() > 0) {
            // choices 배열의 첫 번째 항목에서 "message"의 "content" 필드를 가져옴
            String content = choices.getJSONObject(0).getJSONObject("message").getString("content").trim();
            return content;  // 메시지 내용만 반환
        }

        return "유효한 응답을 찾을 수 없습니다.";  // 유효한 응답이 없는 경우
    }

    // API 호출 중 에러가 발생했을 때 처리하는 메서드
    private Mono<String> handleError(WebClientResponseException e) {
        // 에러 코드에 따른 처리
        if (e.getStatusCode().is4xxClientError()) {
            return Mono.just("Client Error: " + e.getMessage());
        } else if (e.getStatusCode().is5xxServerError()) {
            return Mono.just("Server Error: " + e.getMessage());
        } else {
            return Mono.just("Unexpected Error: " + e.getMessage());
        }
    }
}

