package com.huypham.instagramdemo.di.module;

import android.content.Context;
import android.util.Pair;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.di.ActivityContext;
import com.huypham.instagramdemo.ui.home.HomeMvpPresenter;
import com.huypham.instagramdemo.ui.home.HomeMvpView;
import com.huypham.instagramdemo.ui.home.HomePresenter;
import com.huypham.instagramdemo.ui.login.LoginMvpPresenter;
import com.huypham.instagramdemo.ui.login.LoginMvpView;
import com.huypham.instagramdemo.ui.login.LoginPresenter;
import com.huypham.instagramdemo.ui.login.signUp.SignUpMvpPresenter;
import com.huypham.instagramdemo.ui.login.signUp.SignUpMvpView;
import com.huypham.instagramdemo.ui.login.signUp.SignUpPresenter;
import com.huypham.instagramdemo.ui.main.MainMvpPresenter;
import com.huypham.instagramdemo.ui.main.MainMvpView;
import com.huypham.instagramdemo.ui.main.MainPresenter;
import com.huypham.instagramdemo.ui.splash.SplashMvpPresenter;
import com.huypham.instagramdemo.ui.splash.SplashMvpView;
import com.huypham.instagramdemo.ui.splash.SplashPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.processors.PublishProcessor;

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

    @Provides
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    SignUpMvpPresenter<SignUpMvpView> provideSignUpPresenter(SignUpPresenter<SignUpMvpView> presenter) {
        return presenter;
    }

    @Provides
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    HomeMvpPresenter<HomeMvpView> provideHomePresenter(HomePresenter<HomeMvpView> presenter) {
        return presenter;
    }

    @Provides
    List<Post> providePostList() {
        return new ArrayList<>();
    }

    @Provides
    PublishProcessor<Pair<String, String>> providePagination() {
        return PublishProcessor.create();
    }
}
