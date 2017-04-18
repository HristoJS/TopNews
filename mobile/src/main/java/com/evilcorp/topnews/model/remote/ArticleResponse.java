package com.evilcorp.topnews.model.remote;

import com.evilcorp.topnews.model.local.Article;

import java.util.List;

/**
 * Created by hristo.stoyanov on 4/3/2017.
 */

public class ArticleResponse extends BaseResponse {

    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
