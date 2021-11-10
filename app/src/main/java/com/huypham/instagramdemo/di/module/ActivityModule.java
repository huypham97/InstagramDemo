package com.huypham.instagramdemo.di.module;

import android.content.Context;

import com.huypham.instagramdemo.di.ActivityContext;
import com.huypham.instagramdemo.ui.splash.SplashMvpPresenter;
import com.huypham.instagramdemo.ui.splash.SplashMvpView;
import com.huypham.instagramdemo.ui.splash.SplashPresenter;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }
}
