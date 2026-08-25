package com.rag.ragApp.service;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    @Autowired
    private OllamaEmbeddingModel embeddingModel;


    public float[] generateEmbedding(String text) {
        return embeddingModel.embed(text);
    }

}