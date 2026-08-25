package com.rag.ragApp.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ArticleData {

    String articleNumber;   // e.g., "Article 1"
    String heading;         // e.g., "Name and territory of the Union"
    String part;            // e.g., "Part I – The Union and its Territory"
    String content;         // full text of the Article
    List<Amendment> amendments; // optional: list of amendments affecting this Article
    String sourceUrl;       // e.g., "https://www.constitutionofindia.net/articles/article-1..."
}
