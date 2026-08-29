package com.rag.ragApp.controller;

import com.rag.ragApp.service.FetchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fetch")
public class FetchDataController {
    @Autowired
    private FetchDataService fetchDataService;

    @PostMapping("/coi/data")
    public void inititateFetchCoi(@RequestParam int start, @RequestParam int end){
        fetchDataService.fetchAllArticlesByNumber(start, end);
    }

    @PostMapping("/lawbook/pdf")
    public void initiateFetchPdfsToObject(@RequestParam String path){
        fetchDataService.fetchPdfsTextInJson(path);
    }
}
