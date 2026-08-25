package com.rag.ragApp.service;

import com.rag.ragApp.pojos.ArticleData;
import com.rag.ragApp.service.sources.IFetchDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FetchDataService {
    @Autowired
    private IFetchDataSource iFetchDataSource;

    @Autowired
    private ArticleService articleService;

    public void fetchAllArticlesByNumber(int start, int end){
        //135, 233, 340, 341, 394, 396, 397   missing
        for(int i = start; i <= end; i++){
            log.debug("Article in progress - {}", i);
            String html = iFetchDataSource.fetchDataFromSource(i);
            ArticleData articleData = (ArticleData) iFetchDataSource.parseDataAndReturnRequiredObject(html);
            articleService.saveArticle(articleData);
            try {
                Thread.sleep(500);
            }catch (Exception e){
                log.error("exception sleep - {}", e.getMessage());
            }
        }
    }
}
