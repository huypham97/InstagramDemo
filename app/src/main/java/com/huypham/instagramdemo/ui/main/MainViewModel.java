package com.huypham.instagramdemo.ui.main;

import com.huypham.instagramdemo.ui.base.BaseViewModel;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainViewModel extends BaseViewModel {

    public MainViewModel(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         NetworkUtils networkUtils) {
        super(schedulerProvider, compositeDisposable, networkUtils);
    }

    MutableLiveData<Boolean> homeNavigation = new MutableLiveData<>();
    MutableLiveData<Boolean> profileNavigation = new MutableLiveData<>();
    MutableLiveData<Boolean> photoNavigation = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        homeNavigation.postValue(true);
    }

    public void onHomeSelected() {
        homeNavigation.postValue(true);
    }

    public void onProfileSelected() {
        profileNavigation.postValue(true);
    }

    public void onPhotoSelected() {
        photoNavigation.postValue(true);
    }
}
