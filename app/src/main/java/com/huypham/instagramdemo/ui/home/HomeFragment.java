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
import com.huypham.instagramdemo.di.component.FragmentComponent;
import com.huypham.instagramdemo.ui.base.BaseFragment;
import com.huypham.instagramdemo.ui.home.posts.PostAdapter;
import com.huypham.instagramdemo.ui.main.MainSharedViewModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeFragment extends BaseFragment<HomeViewModel> {

    public static final String TAG = "HomeFragment";

    private ProgressBar pbLoading;
    private RecyclerView rvPosts;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    MainSharedViewModel mainSharedViewModel;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    PostAdapter postAdapter;

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void injectDependencies(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected void setupObserver() {
        super.setupObserver();

        viewModel.posts.observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> postList) {
                postAdapter.appendData(postList);
            }
        });

        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoaded) {
                if (isLoaded)
                    pbLoading.setVisibility(View.VISIBLE);
                else
                    pbLoading.setVisibility(View.GONE);
            }
        });

        viewModel.refreshPostList.observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> postList) {
                postAdapter.updateList(postList);
                rvPosts.scrollToPosition(0);
            }
        });

        mainSharedViewModel.newPost.observe(this, new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                viewModel.onNewPost(post);
            }
        });
    }

    @Override
    protected void setupView(View view) {
        pbLoading = view.findViewById(R.id.pbLoading);
        rvPosts = view.findViewById(R.id.rvPosts);

        rvPosts.setLayoutManager(linearLayoutManager);
        rvPosts.setAdapter(postAdapter);
        rvPosts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("TEST", "onScrolled: getItemCount " + recyclerView.getLayoutManager().getItemCount());
                Log.d("TEST", "onScrolled: findLastCompletelyVisibleItemPosition " + ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition());
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager
                        && recyclerView.getLayoutManager().getItemCount() > 0
                        && recyclerView.getLayoutManager().getItemCount() == ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() + 1) {
                    viewModel.onLoadMore();
                }
            }
        });
    }
}