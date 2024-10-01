package com.example.chatexam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.github.flashvayne.chatgpt.service.ChatgptService;

@Service
public class GptService {

	@Autowired
    private ChatgptService chatgptService;

    public String getChatResponse(String prompt) {
        // ChatGPT 에게 질문을 던집니다.
    	ChatRequest chatRequest =new ChatRequest("gpt-3.5-turbo-instruct",prompt,300,0.7,1.0);
    	
    	ChatResponse chatGptResponse = chatgptService.sendChatRequest(chatRequest);
        String answer = chatGptResponse.getChoices().get(0).getText();
       
        return answer;
    }
}
