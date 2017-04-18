package com.evilcorp.topnews.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evilcorp.topnews.BuildConfig;
import com.evilcorp.topnews.model.local.Source;
import com.evilcorp.topnews.model.remote.SourceFilter;
import com.evilcorp.topnews.model.remote.ArticleResponse;
import com.evilcorp.topnews.model.remote.SourceResponse;
import com.evilcorp.topnews.rest.RestApi;
import com.evilcorp.topnews.rest.RestClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * Created by hristo.stoyanov on 3/31/2017.
 */

public class NewsRepository implements NewsDataSource {
    private static NewsRepository INSTANCE = null;
    private static RestApi restApi;

    private NewsRepository(){
        restApi = RestClient.getApi();
    }

    public static NewsRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (RestClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NewsRepository();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getNewsItems(@NonNull Source source, @Nullable String sort, Callback<ArticleResponse> callback) {
        Map<String, String> query = new HashMap<>();
        query.put("source", source.getId());
        query.put("apiKey", BuildConfig.NEWS_API_KEY);
        if(source.getSortBysAvailable().contains(sort)) {
            query.put("sortBy", sort);
        }
        restApi.getNewsItems(query).enqueue(callback);
    }

    @Override
    public void getSpecificSource(SourceFilter sourceFilter, Callback<SourceResponse> callback){
        Map<String, String> query = new HashMap<>();
        query.put("category", sourceFilter.getCategory());
        query.put("language", sourceFilter.getLanguage());
        query.put("country", sourceFilter.getCountry());
        restApi.getSources(query).enqueue(callback);
    }

    @Override
    public void getAllSources(Callback<SourceResponse> callback){
        restApi.getSources().enqueue(callback);
    }
}
