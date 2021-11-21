package com.huypham.instagramdemo.ui.home.posts;

import android.view.ViewGroup;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.ui.base.BaseAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

public class PostAdapter extends BaseAdapter<Post, PostItemViewHolder> {
    public PostAdapter(Lifecycle parentLifecycle, ArrayList<Post> posts) {
        super(parentLifecycle, posts);
    }

    @NonNull
    @NotNull
    @Override
    public PostItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new PostItemViewHolder(parent);
    }
}
