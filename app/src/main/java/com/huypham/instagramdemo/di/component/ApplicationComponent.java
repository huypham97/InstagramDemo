package com.huypham.instagramdemo.di.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.huypham.instagramdemo.InstagramApplication;
import com.huypham.instagramdemo.data.remote.NetworkService;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.di.ApplicationContext;
import com.huypham.instagramdemo.di.module.ApplicationModule;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(InstagramApplication app);

    Application getApplication();

    @ApplicationContext
    Context getContext();

    NetworkService getNetworkService();

    SharedPreferences getSharedPreferences();

    NetworkUtils getNetworkUtils();

    UserRepository getUserRepository();

    SchedulerProvider getSchedulerProvider();

    CompositeDisposable getCompositeDisposable();

}
