package com.huypham.instagramdemo.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {

    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        setupObserver();
        setupView();
    }

    protected void setupObserver() {
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

    protected abstract void setupView();

    protected abstract int provideLayoutId();
}
