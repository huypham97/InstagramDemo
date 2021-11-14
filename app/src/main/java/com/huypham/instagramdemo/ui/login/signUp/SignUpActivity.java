package com.huypham.instagramdemo.ui.login.signUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
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

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity implements SignUpMvpView {

    @Inject
    SignUpPresenter<SignUpMvpView> presenter;

    ImageView ivLogo;
    EditText etName, etEmail, etPassword;
    Button btnSignUp;
    TextView tvLoginEmail;
    ProgressBar pbLoading;
    private boolean shouldFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
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
        return R.layout.activity_sign_up;
    }

    @Override
    protected void setUpView() {
        ivLogo = findViewById(R.id.ivLogo);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginEmail = findViewById(R.id.tvLoginEmail);
        pbLoading = findViewById(R.id.pbLoading);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSignUp(
                        etEmail.getText().toString(),
                        etPassword.getText().toString(),
                        etName.getText().toString());
            }
        });

        tvLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }

    @Override
    public void openMainActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void openLoginActivity() {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                Pair.create(ivLogo, getString(R.string.shared_element_app_logo)),
                Pair.create(etEmail, getString(R.string.shared_element_email)),
                Pair.create(etPassword, getString(R.string.shared_element_password)));
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class), options.toBundle());
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
}