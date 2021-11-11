package com.huypham.instagramdemo.ui.base;

import androidx.annotation.StringRes;

public interface MvpView {

    void showLoading();

    void hideLoading();

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

}
