package com.rag.ragApp.service;

import com.rag.ragApp.entity.LawBookEntity;
import com.rag.ragApp.pojos.ArticleData;
import com.rag.ragApp.pojos.LawBookPojo;
import com.rag.ragApp.repository.LawBookRepository;
import com.rag.ragApp.service.sources.coidotnet.FetchDataCoiDotNet;
import com.rag.ragApp.service.sources.lawSchoolBooks.FetchDataPDFs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FetchDataService {
    @Autowired
    private FetchDataCoiDotNet fetchDataCoiDotNet;

    @Autowired
    private FetchDataPDFs fetchDataPDFs;

    @Autowired
    private ArticleService articleService;

    public void fetchAllArticlesByNumber(int start, int end){
        //135, 233, 340, 341, 394, 396, 397   missing
        for(int i = start; i <= end; i++){
            log.debug("Article in progress - {}", i);
            String html = fetchDataCoiDotNet.fetchDataFromSource(i);
            ArticleData articleData = (ArticleData) fetchDataCoiDotNet.parseDataAndReturnRequiredObject(html);
            articleService.saveArticle(articleData);
            try {
                Thread.sleep(500);
            }catch (Exception e){
                log.error("exception sleep - {}", e.getMessage());
            }
        }
    }

    public void fetchPdfsTextInJson(String path){
            LawBookEntity lawBookEntity = fetchDataPDFs.fetchDataFromSource(path);
        }

}
