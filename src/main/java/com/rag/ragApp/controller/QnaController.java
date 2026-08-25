package com.rag.ragApp.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna")
public class QnaController {

    @Autowired
    private ChatClient.Builder chatClientBuilder;
    @Autowired
    private VectorStore vectorStore;

    @PostMapping("/ask")
    public String askQuestion(@RequestBody String question) {

        ChatClient researcherAgent = chatClientBuilder
                .defaultSystem("You are a lawyer and talking to a leyman. You speak for Indian Constitution.")
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build()) // Embeds semantic context retrieval
                .build();

        String rawResearchReport = researcherAgent.prompt()
                .user("Analyze data and find documents pertaining to: " + question)
                .call()
                .content();

        // 2. Writer Agent: Consumes researcher output to generate professional text
        ChatClient writerAgent = chatClientBuilder
                .defaultSystem("You are a Lawyer Consultant. Convert complex notes into summaries for leyman clients.")
                .build();

        return writerAgent.prompt()
                .user(String.format("""
                    Format this raw field information into a structured summary report:
                    
                    %s
                    """, rawResearchReport))
                .call()
                .content();
    }

}
