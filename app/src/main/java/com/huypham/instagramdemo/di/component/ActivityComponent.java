package com.huypham.instagramdemo.di.component;

import com.huypham.instagramdemo.di.ActivityScope;
import com.huypham.instagramdemo.di.module.ActivityModule;
import com.huypham.instagramdemo.ui.login.LoginActivity;
import com.huypham.instagramdemo.ui.splash.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

}
