package com.evilcorp.topnews.ui.articles;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.evilcorp.topnews.R;
import com.evilcorp.topnews.adapter.ArticleRecyclerViewAdapter;
import com.evilcorp.topnews.model.local.Article;
import com.evilcorp.topnews.model.local.Source;
import com.evilcorp.topnews.ui.base.BaseActivity;
import com.evilcorp.topnews.ui.webview.WebViewActivity;

import java.util.List;

public class ArticlesActivity extends BaseActivity implements ArticlesContract.View, ArticleRecyclerViewAdapter.OnNewsItemInteractionListener {
    private static final String TAG = ArticlesActivity.class.getSimpleName();
    public static final String ARG_SOURCE = "source";
    private RecyclerView mRecyclerView;
    private ArticlesContract.Presenter mPresenter;
    private Animation mSyncAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_articles);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (getIntent().hasExtra(ARG_SOURCE)) {
            Source source = getIntent().getParcelableExtra(ARG_SOURCE);
            mPresenter = new ArticlesPresenter(this, source);
            actionBar.setTitle(source.getName());
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.newsitem_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        //setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_sync:
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                View syncView = toolbar.findViewById(id);
                startSync(syncView);
                return true;
            case R.id.nav_sort_top:
                mPresenter.sortBy(Article.SortBy.TOP);
                return true;
            case R.id.nav_sort_latest:
                mPresenter.sortBy(Article.SortBy.LATEST);
                return true;
            case R.id.nav_sort_popular:
                mPresenter.sortBy(Article.SortBy.POPULAR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSync(View view) {
        mSyncAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh);
        mSyncAnimation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(mSyncAnimation);
        mPresenter.refresh();
    }

    @Override
    public void showNews(List<Article> sources) {
        if(mSyncAnimation != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSyncAnimation.cancel();
                }
            },1000);
        }
        mRecyclerView.setAdapter(new ArticleRecyclerViewAdapter(this, sources, this));
    }

    @Override
    public void showError(String error) {
        showAlert(error);
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    public void onNewsItemSelected(Article article) {
        Intent webViewIntent = new Intent(this, WebViewActivity.class);
        webViewIntent.putExtra(WebViewActivity.ARG_ARTICLE, article);
        startActivity(webViewIntent);
    }
}
