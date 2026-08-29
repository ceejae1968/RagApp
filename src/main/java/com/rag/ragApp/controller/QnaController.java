package com.rag.ragApp.controller;

import com.rag.ragApp.service.agents.LawyerAgent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna")
public class QnaController {

    @Autowired
    private LawyerAgent lawyerAgent;

    @PostMapping("/ask")
    public String askQuestion(@RequestBody String question) {
        return lawyerAgent.runLawyerAgent(question);
    }

}
