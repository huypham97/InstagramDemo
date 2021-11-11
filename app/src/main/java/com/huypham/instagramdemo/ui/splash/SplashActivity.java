package com.huypham.instagramdemo.ui.splash;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.ui.base.BaseActivity;
import com.huypham.instagramdemo.ui.login.LoginActivity;
import com.huypham.instagramdemo.ui.main.MainActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashMvpPresenter<SplashMvpView> presenter;

    private ImageView ivLogo;

    private boolean shouldFinish = false;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach(SplashActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (shouldFinish)
            finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    protected void injectDependencies(ActivityComponent buildActivityComponent) {
        buildActivityComponent.inject(this);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpView() {
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
    }

    @Override
    public void openLoginActivity() {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        SplashActivity.this,
                        ivLogo,
                        getString(R.string.shared_element_app_logo));
                startActivity(new Intent(getApplicationContext(), LoginActivity.class), options.toBundle());
                shouldFinish = true;
            }
        }.start();
    }

    @Override
    public void openMainActivity() {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }.start();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}