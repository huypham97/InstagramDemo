package com.huypham.instagramdemo.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import com.huypham.instagramdemo.InstagramApplication;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.di.component.DaggerActivityComponent;
import com.huypham.instagramdemo.di.module.ActivityModule;
import com.huypham.instagramdemo.utils.display.ScreenUtils;
import com.huypham.instagramdemo.utils.network.NetworkUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        injectDependencies(buildActivityComponent());
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        ScreenUtils.setLightDisplayStatusBar(this);
        setUpView();
    }

    private ActivityComponent buildActivityComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(((InstagramApplication) getApplication()).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStackImmediate();
        else super.onBackPressed();
    }

    protected abstract void injectDependencies(ActivityComponent buildActivityComponent);

    protected abstract int provideLayoutId();

    protected abstract void setUpView();
}
