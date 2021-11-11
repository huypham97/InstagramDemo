package com.huypham.instagramdemo.ui.login;

import com.huypham.instagramdemo.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onLogin(String email, String password);

    void onSignUpWithEmail();

}
