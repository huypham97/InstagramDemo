package com.huypham.instagramdemo;

import android.app.Application;

import com.huypham.instagramdemo.di.component.ApplicationComponent;
import com.huypham.instagramdemo.di.component.DaggerApplicationComponent;
import com.huypham.instagramdemo.di.module.ApplicationModule;

public class InstagramApplication extends Application {

    private static InstagramApplication instance;

    public static InstagramApplication getInstance() {
        return instance;
    }

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        injectDependencies();
    }

    private void injectDependencies() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
