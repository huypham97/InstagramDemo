package com.huypham.instagramdemo.ui.main;

import androidx.lifecycle.MutableLiveData;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.ui.base.BaseViewModel;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainSharedViewModel extends BaseViewModel {

    public MainSharedViewModel(SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable,
                               NetworkUtils networkUtils) {
        super(schedulerProvider, compositeDisposable, networkUtils);
    }

    @Override
    protected void onCreate() {

    }

    public MutableLiveData<Boolean> homeRedirection = new MutableLiveData<>();
    public MutableLiveData<Boolean> logoutRedirection = new MutableLiveData<>();
    public MutableLiveData<Post> newPost = new MutableLiveData<>();

    public void onHomeRedirection() {
        homeRedirection.postValue(true);
    }

    public void onLogoutRedirection() {
        logoutRedirection.postValue(true);
    }

}
