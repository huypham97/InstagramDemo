package com.huypham.instagramdemo.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginPresenter<LoginMvpView> presenter;

    ImageView ivLogo;
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvSignUpEmail;
    ProgressBar pbLoading;

    private boolean shouldFinish = false;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (shouldFinish)
            finish();
    }

    @Override
    protected void injectDependencies(ActivityComponent buildActivityComponent) {
        buildActivityComponent.inject(this);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpView() {
        ivLogo = findViewById(R.id.ivLogo);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUpEmail = findViewById(R.id.tvSignUpEmail);
        pbLoading = findViewById(R.id.pbLoading);

        Drawable clearIcon = ContextCompat.getDrawable(this, R.drawable.ic_cancel);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLogin(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

        tvSignUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });

    }

    @Override
    public void openMainActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void openSignUpActivity() {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                Pair.create(ivLogo, getString(R.string.shared_element_app_logo)),
                Pair.create(etEmail, getString(R.string.shared_element_email)),
                Pair.create(etPassword, getString(R.string.shared_element_password)));
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class), options.toBundle());
        shouldFinish = true;
    }

    @Override
    public void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}