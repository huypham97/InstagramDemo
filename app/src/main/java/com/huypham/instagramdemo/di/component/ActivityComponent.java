package com.huypham.instagramdemo.di.component;

import com.huypham.instagramdemo.di.scope.ActivityScope;
import com.huypham.instagramdemo.di.module.ActivityModule;
import com.huypham.instagramdemo.ui.login.LoginActivity;
import com.huypham.instagramdemo.ui.login.signUp.SignUpActivity;
import com.huypham.instagramdemo.ui.main.MainActivity;
import com.huypham.instagramdemo.ui.splash.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

    void inject(SignUpActivity activity);

    void inject(MainActivity activity);
}
