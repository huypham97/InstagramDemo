package com.huypham.instagramdemo.ui.home;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomeFragment extends BaseFragment implements HomeMvpView {

    public static final String TAG = "HomeFragment";

    ProgressBar pbLoading;

    @Inject
    HomeMvpPresenter<HomeMvpView> presenter;

    private HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        presenter.onAttach(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void injectDependencies(ActivityComponent buildActivityComponent) {
        buildActivityComponent.inject(this);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpView(View view) {
        Log.d("TEST", "setUpView: ");
        pbLoading = view.findViewById(R.id.pbLoading);


        presenter.onViewPrepared();
    }

    @Override
    public void updatePost() {

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
    public boolean checkInternetConnectionWithMessage() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}