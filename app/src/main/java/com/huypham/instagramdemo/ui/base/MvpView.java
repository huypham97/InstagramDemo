package com.huypham.instagramdemo.ui.base;

import androidx.annotation.StringRes;

public interface MvpView {

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

}
