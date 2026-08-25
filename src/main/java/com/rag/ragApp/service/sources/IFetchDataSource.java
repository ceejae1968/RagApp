package com.rag.ragApp.service.sources;

public interface IFetchDataSource {
    public String fetchDataFromSource(int articleNum);
    public Object parseDataAndReturnRequiredObject(String html);
}
