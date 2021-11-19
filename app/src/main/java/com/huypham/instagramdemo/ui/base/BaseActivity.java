package com.huypham.instagramdemo.ui.base;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.huypham.instagramdemo.InstagramApplication;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.di.component.DaggerActivityComponent;
import com.huypham.instagramdemo.di.module.ActivityModule;
import com.huypham.instagramdemo.utils.display.ScreenUtils;

import javax.inject.Inject;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    @Inject
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies(buildActivityComponent());
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        ScreenUtils.setLightDisplayStatusBar(this);
        setupObserver();
        setupView(savedInstanceState);
        viewModel.onCreate();
    }

    private ActivityComponent buildActivityComponent() {
        return DaggerActivityComponent
                .builder()
                .applicationComponent(((InstagramApplication) getApplication()).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected void setupObserver() {
        Log.d(TAG, "setupObserver: " + viewModel);
        viewModel.messageString.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                showMessage(message);
            }
        });

        viewModel.messageStringId.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer resId) {
                showMessage(resId);
            }
        });
    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStackImmediate();
        else
            super.onBackPressed();
    }

    protected abstract int provideLayoutId();

    protected abstract void injectDependencies(ActivityComponent activityComponent);

    protected abstract void setupView(Bundle savedInstanceState);
}
