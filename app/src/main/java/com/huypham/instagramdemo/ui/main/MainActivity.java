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

public class MainActivity extends BaseActivity implements MainMvpView {

    private Fragment activeFragment = null;

    BottomNavigationView bottomNavView;

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

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
    protected void injectDependencies(ActivityComponent buildActivityComponent) {
        buildActivityComponent.inject(this);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        bottomNavView = findViewById(R.id.bottomNavView);

        bottomNavView.setItemIconTintList(null);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        presenter.onHomeSelected();
                        return true;
                    case R.id.itemPhoto:
                        presenter.onPhotoSelected();
                        return true;
                    case R.id.itemProfile:
                        presenter.onProfileSelected();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void showHome() {
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

    @Override
    public void showProfile() {
        if (activeFragment instanceof ProfileFragment)
            return;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);

        if (fragment == null) {
            fragment = ProfileFragment.newInstance();
            fragmentTransaction.add(R.id.containerFragment, fragment, ProfileFragment.TAG);
        } else {
            fragmentTransaction.show(fragment);
        }

        if (activeFragment != null)
            fragmentTransaction.hide(activeFragment);

        fragmentTransaction.commit();

        activeFragment = fragment;
    }

    @Override
    public void showAddPhoto() {
        if (activeFragment instanceof PhotoFragment)
            return;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        PhotoFragment fragment = (PhotoFragment) getSupportFragmentManager().findFragmentByTag(PhotoFragment.TAG);

        if (fragment == null) {
            fragment = PhotoFragment.newInstance();
            fragmentTransaction.add(R.id.containerFragment, fragment, PhotoFragment.TAG);
        } else {
            fragmentTransaction.show(fragment);
        }

        if (activeFragment != null)
            fragmentTransaction.hide(activeFragment);

        fragmentTransaction.commit();

        activeFragment = fragment;
    }

    @Override
    public void homeRedirection() {
        bottomNavView.setSelectedItemId(R.id.itemHome);
    }

    @Override
    public void logOutRedirection() {
        startActivity(new Intent(MainActivity.this, SplashActivity.class));
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}