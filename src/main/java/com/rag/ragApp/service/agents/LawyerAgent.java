package com.rag.ragApp.service.agents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LawyerAgent {
    @Autowired
    private ChatClient.Builder chatClientBuilder;
    @Autowired
    private VectorStore vectorStore;

    public String runLawyerAgent(String question){
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(3)
                        .build()
        );

        log.info("VectorStore retrieved docs count: {}", docs.size());
        docs.forEach(doc -> log.info("VectorStore doc: {}", doc.getText()));
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
