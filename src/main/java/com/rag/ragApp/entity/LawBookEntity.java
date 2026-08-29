package com.rag.ragApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "law_books_store")
@Data
public class LawBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chapter")
    private String chapter;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    // pgvector column for embeddings
    @Column(name = "embedding", columnDefinition = "vector(1024)")
    private float[] embedding;
}
