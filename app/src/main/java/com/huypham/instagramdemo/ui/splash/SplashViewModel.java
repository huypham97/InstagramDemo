package com.huypham.instagramdemo.ui.splash;

import androidx.lifecycle.MutableLiveData;

import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BaseViewModel;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SplashViewModel extends BaseViewModel {

    private UserRepository userRepository;

    public SplashViewModel(SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable,
                           NetworkUtils networkUtils,
                           UserRepository userRepository) {
        super(schedulerProvider, compositeDisposable, networkUtils);
        this.userRepository = userRepository;
    }

    MutableLiveData<Map<String, String>> launchMain = new MutableLiveData<>();
    MutableLiveData<Map<String, String>> launchLogin = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        if (userRepository.getCurrentUser() != null) {
            launchMain.postValue(Collections.emptyMap());
        } else {
            launchLogin.postValue(Collections.emptyMap());
        }
    }
}
