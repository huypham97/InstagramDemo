package com.huypham.instagramdemo.ui.base;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface MvpViewHolder<P extends MvpPresenter> {

    public void attachPresenter(P presenter);

    public void detachPresenter();
}
