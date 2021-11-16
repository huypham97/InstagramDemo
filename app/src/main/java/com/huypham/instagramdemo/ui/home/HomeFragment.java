package com.huypham.instagramdemo.ui.home;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.ui.base.BaseFragment;
import com.huypham.instagramdemo.ui.home.posts.PostAdapter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeFragment extends BaseFragment implements HomeMvpView {

    public static final String TAG = "HomeFragment";

    RecyclerView rvPosts;
    ProgressBar pbLoading;

    @Inject
    HomeMvpPresenter<HomeMvpView> presenter;

    @Inject
    PostAdapter postAdapter;

    @Inject
    LinearLayoutManager layoutManager;

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
        rvPosts = view.findViewById(R.id.rvPosts);
        pbLoading = view.findViewById(R.id.pbLoading);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPosts.setLayoutManager(layoutManager);
        rvPosts.setItemAnimator(new DefaultItemAnimator());
        rvPosts.setAdapter(postAdapter);

        presenter.onViewPrepared();
    }

    @Override
    public void updatePost(List<Post> postList) {
        postAdapter.addItems(postList);
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