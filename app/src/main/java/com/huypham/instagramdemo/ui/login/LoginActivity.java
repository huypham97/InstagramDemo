package com.huypham.instagramdemo.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.ui.base.BaseActivity;
import com.huypham.instagramdemo.ui.splash.SplashActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Override
    protected void injectDependencies(ActivityComponent buildActivityComponent) {

    }

    @Override
    protected int provideLayoutId() {
        return 0;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void openSignUpActivity() {

    }
}