package com.huypham.instagramdemo.ui.base;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<P extends BasePresenter> extends RecyclerView.ViewHolder implements MvpViewHolder<P> {

    protected P presenter;
    private BaseActivity activity;

    public BaseViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        activity = (BaseActivity) itemView.getContext();
    }

    @Override
    public void attachPresenter(P presenter) {
        this.presenter = presenter;
        presenter.onAttach(this);
    }

    @Override
    public void detachPresenter() {
        presenter = null;
    }

    @Override
    public void showLoading() {
        activity.showLoading();
    }

    @Override
    public void hideLoading() {
        activity.hideLoading();
    }

    @Override
    public void showMessage(String message) {
        activity.showMessage(message);
    }

    @Override
    public void showMessage(int resId) {
        activity.showMessage(resId);
    }

    @Override
    public boolean isNetworkConnected() {
        return activity.isNetworkConnected();
    }

    @Override
    public boolean checkInternetConnectionWithMessage() {
        return activity.checkInternetConnectionWithMessage();
    }
}
