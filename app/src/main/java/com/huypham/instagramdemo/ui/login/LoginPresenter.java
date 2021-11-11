package com.huypham.instagramdemo.ui.login;

import com.huypham.instagramdemo.ui.base.BasePresenter;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {
    public LoginPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLogin(String email, String password) {

    }

    @Override
    public void onSignUpWithEmail() {

    }
}
