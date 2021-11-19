package com.huypham.instagramdemo.ui.login.signUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.ui.base.BaseActivity;
import com.huypham.instagramdemo.ui.login.LoginActivity;
import com.huypham.instagramdemo.ui.main.MainActivity;

import java.util.Map;

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity<SignUpViewModel> {

    private boolean shouldFinish = false;

    private ImageView ivLogo;
    private EditText etName, etEmail, etPassword;
    private Button btnSignUp;
    private TextView tvLoginEmail;
    private ProgressBar pbLoading;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (shouldFinish)
            finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

    @Override
    protected void setupView(Bundle savedInstanceState) {
        ivLogo = findViewById(R.id.ivLogo);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginEmail = findViewById(R.id.tvLoginEmail);
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

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onNameChanged(s.toString());
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onSignUp();
            }
        });

        tvLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        SignUpActivity.this,
                        Pair.create(ivLogo, getString(R.string.shared_element_app_logo)),
                        Pair.create(etEmail, getString(R.string.shared_element_email)),
                        Pair.create(etPassword, getString(R.string.shared_element_password)));
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class), options.toBundle());
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
}