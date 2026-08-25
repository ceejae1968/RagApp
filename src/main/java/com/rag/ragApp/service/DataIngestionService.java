package com.rag.ragApp.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DataIngestionService {

    private final VectorStore vectorStore;

    public DataIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingestRawText(String rawPayload) {
        // 1. Wrap your content inside a Spring AI Document wrapper
        Document document = new Document(rawPayload);

        // 2. Split large passages into small, digestible chunks for the LLM
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(512)
                .withMinChunkSizeChars(150)
                .withMinChunkLengthToEmbed(5)
                .withMaxNumChunks(10000)
                .withKeepSeparator(true)
                .build();

        List<Document> chunks = splitter.apply(List.of(document));

        // 3. Generate embeddings and save to your Docker pgvector database
        vectorStore.accept(chunks);
    }
}