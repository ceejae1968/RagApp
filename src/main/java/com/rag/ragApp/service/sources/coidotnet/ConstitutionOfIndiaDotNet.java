package com.rag.ragApp.service.sources.coidotnet;

import com.rag.ragApp.pojos.ArticleData;
import com.rag.ragApp.service.sources.IFetchDataSource;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ConstitutionOfIndiaDotNet implements IFetchDataSource {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String fetchDataFromSource(int articleNum) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.constitutionofindia.net/articles/article-" + articleNum;
        return restTemplate.getForObject(url, String.class);
    }

    @Override
    public Object parseDataAndReturnRequiredObject(String html) {
        Document document = Jsoup.parse(html, "UTF-8");

        String part = textOf(
                document.selectFirst(".article-detail__part-no")
        );

        String articleNumber = textOf(
                document.selectFirst(".article-detail__article-no .number-field")
        );

        String heading = textOf(
                document.selectFirst(".article-detail__intro h1")
        );

        String content = textOf(
                document.selectFirst(".article-detail__intro-content")
        );
        ArticleData articleData = new ArticleData(articleNumber, heading, part, content, null, null);
        log.debug("Article Data - {}", articleData);
        return articleData;
    }

    private String textOf(Element element) {
        return element == null ? "" : element.text().trim();
    }
}
