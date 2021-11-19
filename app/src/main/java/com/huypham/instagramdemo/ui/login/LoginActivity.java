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

public class LoginActivity extends BaseActivity<LoginViewModel> {

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {

    }

    @Override
    protected void setupView(Bundle savedInstanceState) {

    }
}