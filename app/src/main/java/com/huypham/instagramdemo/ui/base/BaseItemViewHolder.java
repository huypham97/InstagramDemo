package com.huypham.instagramdemo.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huypham.instagramdemo.InstagramApplication;
import com.huypham.instagramdemo.di.component.DaggerViewHolderComponent;
import com.huypham.instagramdemo.di.component.ViewHolderComponent;
import com.huypham.instagramdemo.di.module.ViewHolderModule;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseItemViewHolder<T, VM extends BaseItemViewModel<T>> extends RecyclerView.ViewHolder implements LifecycleOwner {

    public BaseItemViewHolder(@LayoutRes int layoutId, ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        onCreate();
    }

    @Inject
    protected VM viewModel;

    @Inject
    LifecycleRegistry lifecycleRegistry;

    @NonNull
    @NotNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    public void bind(T data) {
        viewModel.updateData(data);
    }

    protected void onCreate() {
        injectDependencies(buildViewHolderComponent());
        lifecycleRegistry.markState(Lifecycle.State.INITIALIZED);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
        setupObserver();
        setupView(itemView);
    }

    public void onStart() {
        lifecycleRegistry.markState(Lifecycle.State.STARTED);
        lifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    public void onStop() {
        lifecycleRegistry.markState(Lifecycle.State.STARTED);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    public void onDestroy() {
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    private ViewHolderComponent buildViewHolderComponent() {
        return DaggerViewHolderComponent
                .builder()
                .applicationComponent(((InstagramApplication) itemView.getContext().getApplicationContext()).getComponent())
                .viewHolderModule(new ViewHolderModule(this))
                .build();
    }

    public void showMessage(String message) {
        Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(@StringRes int resId) {
        showMessage(itemView.getContext().getString(resId));
    }

    protected void setupObserver() {

        viewModel.messageString.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                showMessage(message);
            }
        });

        viewModel.messageStringId.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer resId) {
                showMessage(resId);
            }
        });

    }

    protected abstract void injectDependencies(ViewHolderComponent buildViewHolderComponent);

    protected abstract void setupView(View view);
}
