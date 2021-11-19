package com.huypham.instagramdemo.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.ui.base.BaseActivity;
import com.huypham.instagramdemo.ui.home.HomeFragment;
import com.huypham.instagramdemo.ui.photo.PhotoFragment;
import com.huypham.instagramdemo.ui.profile.ProfileFragment;
import com.huypham.instagramdemo.ui.splash.SplashActivity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainViewModel> {

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {

    }

    @Override
    protected void setupView(Bundle savedInstanceState) {

    }
}