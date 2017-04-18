package com.evilcorp.topnews.ui.sources;

import com.evilcorp.topnews.model.local.Source;
import com.evilcorp.topnews.model.remote.SourceFilter;
import com.evilcorp.topnews.ui.base.BaseContract;

import java.util.List;

/**
 * Created by hristo.stoyanov on 4/3/2017.
 */

public interface SourceContract {
    interface View extends BaseContract.View {
        void showSources(List<Source> sources);
    }

    interface Presenter extends BaseContract.Presenter {
        void filterSources(SourceFilter sourceFilter);
    }
}
