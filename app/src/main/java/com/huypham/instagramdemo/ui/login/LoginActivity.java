package com.huypham.instagramdemo.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.ui.base.BaseActivity;
import com.huypham.instagramdemo.ui.login.signUp.SignUpActivity;
import com.huypham.instagramdemo.ui.main.MainActivity;
import com.huypham.instagramdemo.ui.splash.SplashActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    private boolean shouldFinish = false;

    private ImageView ivLogo;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignUpEmail;
    private ProgressBar pbLoading;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void setupView(Bundle savedInstanceState) {
        ivLogo = findViewById(R.id.ivLogo);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUpEmail = findViewById(R.id.tvSignUpEmail);
        pbLoading = findViewById(R.id.pbLoading);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onEmailChanged(s.toString());
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onPasswordChanged(s.toString());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onLogin();
            }
        });

        tvSignUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        LoginActivity.this,
                        Pair.create(ivLogo, getString(R.string.shared_element_app_logo)),
                        Pair.create(etEmail, getString(R.string.shared_element_email)),
                        Pair.create(etPassword, getString(R.string.shared_element_password)));
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class), options.toBundle());
                shouldFinish = true;
            }
        });
    }

    @Override
    protected void setupObserver() {
        super.setupObserver();

        viewModel.launchMain.observe(this, new Observer<Map<String, String>>() {
            @Override
            public void onChanged(Map<String, String> stringStringMap) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        viewModel.loggingIn.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLogging) {
                if (isLogging)
                    pbLoading.setVisibility(View.VISIBLE);
                else
                    pbLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (shouldFinish) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}