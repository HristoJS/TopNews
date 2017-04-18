package com.evilcorp.topnews.ui.base;

/**
 * Created by hristo.stoyanov on 4/3/2017.
 */

public interface BaseContract {
    interface View {
        void showError(String error);

        void showProgress(String msg);

        void dismissProgress();

        boolean isActive();
    }

    interface Presenter {
        void onStart();
    }
}
