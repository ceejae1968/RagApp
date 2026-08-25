package com.rag.ragApp.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class MultiAgentRagsCrew {

    private final ChatClient.Builder chatClientBuilder;
    private final VectorStore vectorStore; // Wired from your pgvector or bedrock starter

    public MultiAgentRagsCrew(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClientBuilder = chatClientBuilder;
        this.vectorStore = vectorStore;
    }

    public String runCrewWorkflow(String userObjective) {

        // 1. Researcher Agent: Automatically searches your Vector Store for information
        ChatClient researcherAgent = chatClientBuilder
                .defaultSystem("You are a diligent facts researcher. Extract details exactly as found.")
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build()) // Embeds semantic context retrieval
                .build();

        String rawResearchReport = researcherAgent.prompt()
                .user("Analyze data and find documents pertaining to: " + userObjective)
                .call()
                .content();

        // 2. Writer Agent: Consumes researcher output to generate professional text
        ChatClient writerAgent = chatClientBuilder
                .defaultSystem("You are a Technical Publisher. Convert complex notes into summaries.")
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