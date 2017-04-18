package com.evilcorp.topnews.ui.articles;

import com.evilcorp.topnews.model.local.Article;
import com.evilcorp.topnews.ui.base.BaseContract;

import java.util.List;

/**
 * Created by hristo.stoyanov on 4/3/2017.
 */

public interface ArticlesContract {
    interface View extends BaseContract.View {
        void showNews(List<Article> sources);
    }

    interface Presenter extends BaseContract.Presenter {
        void sortBy(String sortType);

        void refresh();
    }
}
