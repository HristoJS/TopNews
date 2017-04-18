package com.evilcorp.topnews.repository;

import com.evilcorp.topnews.model.local.Source;
import com.evilcorp.topnews.model.remote.SourceFilter;
import com.evilcorp.topnews.model.remote.ArticleResponse;
import com.evilcorp.topnews.model.remote.SourceResponse;

import retrofit2.Callback;

/**
 * Created by hristo.stoyanov on 4/4/2017.
 */

public interface NewsDataSource {
    void getNewsItems(Source source, String sort, Callback<ArticleResponse> callback);

    void getSpecificSource(SourceFilter sourceFilter, Callback<SourceResponse> callback);

    void getAllSources(Callback<SourceResponse> callback);
}
