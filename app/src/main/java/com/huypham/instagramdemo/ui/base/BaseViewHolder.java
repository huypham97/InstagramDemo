package com.huypham.instagramdemo.ui.base;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private int currentPosition;

    public BaseViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position) {
        currentPosition = position;
        clear();
    }
}
