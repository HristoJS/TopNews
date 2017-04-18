package com.evilcorp.topnews.ui.sources;

import com.google.gson.Gson;

import android.util.Log;

import com.evilcorp.topnews.model.local.Source;
import com.evilcorp.topnews.model.remote.ResponseError;
import com.evilcorp.topnews.model.remote.SourceFilter;
import com.evilcorp.topnews.model.remote.SourceResponse;
import com.evilcorp.topnews.repository.NewsRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by hristo.stoyanov on 4/3/2017.
 */

public class SourcePresenter implements SourceContract.Presenter, Callback<SourceResponse> {
    private static final String TAG = SourcePresenter.class.getSimpleName();
    private SourceContract.View mView;

    public SourcePresenter(SourceContract.View view){
        mView = view;
    }

    @Override
    public void onStart() {
        loadSources();
    }

    private void loadSources(){
        mView.showProgress("Loading news sources...");
        NewsRepository.getInstance().getAllSources(this);
    }

    @Override
    public void onResponse(Call<SourceResponse> call, retrofit2.Response<SourceResponse> response) {
        if(response.isSuccessful()){
            List<Source> sourcesList = response.body().getSources();
            mView.showSources(sourcesList);
        }
        else {
            try {
                String responseError = response.errorBody().string();
                ResponseError error = new Gson().fromJson(responseError, ResponseError.class);
                String errorMessage = error.getMessage();
                Log.e(TAG, errorMessage);
                mView.showError(errorMessage);
            } catch (IOException e) {
                e.printStackTrace();
                mView.showError(e.getLocalizedMessage());
            }
        }
        mView.dismissProgress();
    }

    @Override
    public void onFailure(Call<SourceResponse> call, Throwable t) {
        Log.d(TAG, t.getLocalizedMessage());
        mView.showError("Unable to get news sources");
        mView.dismissProgress();
    }

    @Override
    public void filterSources(SourceFilter sourceFilter) {
        NewsRepository.getInstance().getSpecificSource(sourceFilter, this);
    }
}
