package com.huypham.instagramdemo.ui.splash;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Observer;

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

import java.util.Map;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<SplashViewModel> {

    public static final String TAG = "SplashActivity";

    private ImageView ivLogo;

    private boolean shouldFinish = false;

    @Override
    protected void onStop() {
        super.onStop();
        if (shouldFinish) {
            finish();
        }
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void setupView(Bundle savedInstanceState) {
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
    }

    @Override
    protected void setupObserver() {
        super.setupObserver();
        viewModel.launchMain.observe(this, new Observer<Map<String, String>>() {
            @Override
            public void onChanged(Map<String, String> stringStringMap) {
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
        });

        viewModel.launchLogin.observe(this, new Observer<Map<String, String>>() {
            @Override
            public void onChanged(Map<String, String> stringStringMap) {
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
        });
    }
}