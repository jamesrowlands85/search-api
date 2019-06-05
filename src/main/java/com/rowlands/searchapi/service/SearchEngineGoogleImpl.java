package com.rowlands.searchapi.service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.rowlands.searchapi.api.SearchApiResponse;
import com.rowlands.searchapi.config.SearchProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchEngineGoogleImpl implements SearchEngine {

    @Autowired
    private SearchProperties searchProperties;

    private static final Logger logger = LoggerFactory.getLogger(SearchEngineGoogleImpl.class);

    @Override
    public SearchApiResponse search(String queryString) {

        List<Result> resultList = googleCustomSearch(queryString);

        return getSearchApiSecondResponse(resultList);
    }

    private List<Result> googleCustomSearch(String queryString) {
        Customsearch customsearch= null;

        try {
            customsearch = new Customsearch(new NetHttpTransport(),new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) {
                    try {
                        // set connect and read timeouts
                        httpRequest.setConnectTimeout(searchProperties.getConnectTimeout());
                        httpRequest.setReadTimeout(searchProperties.getReadTimeout());

                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Result> resultList=null;
        try {
            Customsearch.Cse.List list=customsearch.cse().list(queryString);
            list.setKey(searchProperties.getApiKey());
            list.setCx(searchProperties.getSearchEngineId());
            Search results=list.execute();
            resultList=results.getItems();
        }
        catch (  Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private SearchApiResponse getSearchApiSecondResponse(List<Result> resultList) {
        if(resultList != null && resultList.size() > 1) {
            Result secondItem = resultList.get(1);
            return new SearchApiResponse(secondItem.getHtmlTitle(), secondItem.getFormattedUrl());
        }
        return new SearchApiResponse();
    }

}
