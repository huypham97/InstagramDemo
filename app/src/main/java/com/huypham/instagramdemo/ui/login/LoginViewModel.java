package com.huypham.instagramdemo.ui.login;

import com.huypham.instagramdemo.ui.base.BaseViewModel;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginViewModel extends BaseViewModel {
    public LoginViewModel(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, NetworkUtils networkUtils) {
        super(schedulerProvider, compositeDisposable, networkUtils);
    }

    @Override
    protected void onCreate() {

    }
}
