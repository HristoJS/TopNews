package com.evilcorp.topnews.rest;

import com.evilcorp.topnews.model.remote.ArticleResponse;
import com.evilcorp.topnews.model.remote.SourceResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by hristo.stoyanov on 3/31/2017.
 */

public interface RestApi {

    @GET("articles")
    Call<ArticleResponse> getNewsItems(@QueryMap Map<String, String> query);

    @GET("sources")
    Call<SourceResponse> getSources(@QueryMap Map<String, String> query);

    @GET("sources")
    Call<SourceResponse> getSources();

}
