package com.rag.ragApp.entity;

import com.rag.ragApp.pojos.Amendment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "constitution_store")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "article_number", nullable = false)
    private String articleNumber;

    @Column(name = "heading", nullable = false)
    private String heading;

    @Column(name = "part")
    private String part;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Amendment>  amendments; // store as JSON string

    @Column(name = "source_url")
    private String sourceUrl;

    // pgvector column: map as float[] (requires custom converter)
    @Column(name = "embedding", columnDefinition = "vector(1536)")
    private float[] embedding;
}
