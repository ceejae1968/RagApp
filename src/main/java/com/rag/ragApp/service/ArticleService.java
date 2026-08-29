package com.rag.ragApp.service;

import com.rag.ragApp.pojos.ArticleData;
import com.rag.ragApp.entity.ArticleEntity;
import com.rag.ragApp.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class ArticleService {

    @Autowired
    private ArticleRepository repository;
    @Autowired
    private EmbeddingService embeddingService;

    @Transactional
    public void saveArticle(ArticleData parsed) {
        String text = "Part - " + parsed.getPart() +", Article - " + parsed.getArticleNumber() + " , Heading : " + parsed.getHeading() + ": " + parsed.getContent();
        log.debug("Embedding Text - {}", text);
        float[] embedding = embeddingService.generateEmbedding(
                text
        );

        ArticleEntity entity = ArticleEntity.builder()
                .articleNumber(parsed.getArticleNumber())
                .heading(parsed.getHeading())
                .part(parsed.getPart())
                .content(parsed.getContent())
                .amendments(parsed.getAmendments() == null ? new ArrayList<>() : parsed.getAmendments())
                .sourceUrl(parsed.getSourceUrl())
                .embedding(embedding)
                .build();

        repository.save(entity);
        log.info("Save Success - {}", entity);
    }
}
