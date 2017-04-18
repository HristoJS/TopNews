package com.evilcorp.topnews.ui.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.evilcorp.topnews.R;
import com.evilcorp.topnews.model.local.Article;
import com.evilcorp.topnews.ui.base.BaseActivity;

/**
 * Created by hristo.stoyanov on 4/4/2017.
 */

public class WebViewActivity extends BaseActivity {
    public static final String ARG_ARTICLE = "article";
    private Article mArticle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        if (getIntent().hasExtra(ARG_ARTICLE)) {
            mArticle = getIntent().getParcelableExtra(ARG_ARTICLE);
        }
        WebView webView = (WebView) findViewById(R.id.webView);
        setupWebView(webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView(WebView webView) {
        showProgress("Loading article...");
        webView.loadUrl(mArticle.getUrl());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dismissProgress();
            }
        });
    }

}
