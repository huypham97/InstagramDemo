package com.huypham.instagramdemo.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

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

    private Fragment activeFragment = null;

    private BottomNavigationView bottomNavView;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void setupView(Bundle savedInstanceState) {
        bottomNavView = findViewById(R.id.bottomNavView);

        bottomNavView.setItemIconTintList(null);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        viewModel.onHomeSelected();
                        return true;
                    case R.id.itemPhoto:
                        viewModel.onPhotoSelected();
                        return true;
                    case R.id.itemProfile:
                        viewModel.onProfileSelected();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void setupObserver() {
        super.setupObserver();
        viewModel.homeNavigation.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showHome();
            }
        });

        viewModel.photoNavigation.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showAddPhoto();
            }
        });

        viewModel.profileNavigation.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showProfile();
            }
        });
    }

    private void showHome() {
        if (activeFragment instanceof HomeFragment)
            return;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);

        if (fragment == null) {
            fragment = HomeFragment.newInstance();
            fragmentTransaction.add(R.id.containerFragment, fragment, HomeFragment.TAG);
        } else {
            fragmentTransaction.show(fragment);
        }

        if (activeFragment != null)
            fragmentTransaction.hide(activeFragment);

        fragmentTransaction.commit();

        activeFragment = fragment;
    }

    private void showProfile() {
    }

    private void showAddPhoto() {
    }
}