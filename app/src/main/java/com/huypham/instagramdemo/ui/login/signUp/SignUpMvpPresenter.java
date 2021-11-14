package com.huypham.instagramdemo.ui.login.signUp;

import com.huypham.instagramdemo.ui.base.MvpPresenter;

public interface SignUpMvpPresenter<V extends SignUpMvpView> extends MvpPresenter<V> {

    void onSignUp(String name, String email, String password);

}
