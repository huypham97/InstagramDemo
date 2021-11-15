package com.huypham.instagramdemo.ui.base;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<P extends BasePresenter> extends RecyclerView.ViewHolder implements MvpViewHolder<P> {

    protected P presenter;

    public BaseViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }

    @Override
    public void attachPresenter(P presenter) {

    }

    @Override
    public void detachPresenter() {

    }
}
