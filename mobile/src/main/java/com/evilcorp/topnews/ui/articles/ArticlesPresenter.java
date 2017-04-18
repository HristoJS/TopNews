package com.evilcorp.topnews.ui.articles;

import com.google.gson.Gson;

import android.util.Log;

import com.evilcorp.topnews.model.local.Article;
import com.evilcorp.topnews.model.remote.ArticleResponse;
import com.evilcorp.topnews.model.local.Source;
import com.evilcorp.topnews.model.remote.BaseResponse;
import com.evilcorp.topnews.model.remote.ResponseError;
import com.evilcorp.topnews.repository.NewsRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hristo.stoyanov on 4/3/2017.
 */

public class ArticlesPresenter implements ArticlesContract.Presenter, Callback<ArticleResponse> {
    private static final String TAG = ArticlesPresenter.class.getSimpleName();
    private ArticlesContract.View mView;
    private Source mSource;
    private String mSortType;

    public ArticlesPresenter(ArticlesContract.View view, Source source){
        mView = view;
        mSource = source;
    }

    @Override
    public void onStart() {
        getItems();
    }

    private void getItems(){
        NewsRepository.getInstance().getNewsItems(mSource, mSortType , this);
    }

    @Override
    public void onResponse(Call<ArticleResponse> call, retrofit2.Response<ArticleResponse> response) {
        if(response.isSuccessful()){
            List<Article> sourcesList = response.body().getArticles();
            mView.showNews(sourcesList);
        }
        else {
            ResponseError error = new Gson().fromJson(response.errorBody().toString(), ResponseError.class);
            String errorMessage = error.getMessage();
            Log.e(TAG, errorMessage);
            mView.showError(errorMessage);
        }
    }

    @Override
    public void onFailure(Call<ArticleResponse> call, Throwable t) {
        mView.showError("Unable to get any news");
        Log.d(TAG, t.getLocalizedMessage());
    }

    @Override
    public void sortBy(String sortType) {
        mSortType = sortType;
        getItems();
    }

    @Override
    public void refresh() {
        getItems();
    }
}
