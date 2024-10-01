package com.example.gptexam;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Mono;

@Controller
@ResponseBody
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    // 사용자가 질문을 POST 요청으로 보냄, 대화 히스토리를 옵션으로 처리
    @PostMapping("/ask")
    public Mono<String> askQuestion(@RequestBody RequestData requestData) {
        // 서비스 호출, 대화 히스토리가 없을 경우 null 처리
        return openAIService.getChatCompletion(requestData.getQuestion(), requestData.getConversationHistory());
    }

    // 요청 데이터 포맷을 위한 내부 클래스
    public static class RequestData {
        private String question;
        private List<Map<String, String>> conversationHistory;

        // Getters and Setters
        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public List<Map<String, String>> getConversationHistory() {
            return conversationHistory;
        }

        public void setConversationHistory(List<Map<String, String>> conversationHistory) {
            this.conversationHistory = conversationHistory;
        }
    }
}
