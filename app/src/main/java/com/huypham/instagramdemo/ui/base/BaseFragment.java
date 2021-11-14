package com.huypham.instagramdemo.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huypham.instagramdemo.InstagramApplication;
import com.huypham.instagramdemo.di.component.ActivityComponent;
import com.huypham.instagramdemo.di.component.DaggerActivityComponent;
import com.huypham.instagramdemo.di.module.ActivityModule;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements MvpView {

    private BaseActivity activity;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        injectDependencies(buildActivityComponent());
        super.onCreate(savedInstanceState);
    }

    private ActivityComponent buildActivityComponent() {
        return activity.getActivityComponent();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(provideLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView(view);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.activity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void showMessage(String message) {
        if (activity != null) {
            activity.showMessage(message);
        }
    }

    @Override
    public void showMessage(int resId) {
        if (activity != null) {
            activity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (activity != null) {
            return activity.isNetworkConnected();
        }
        return false;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    protected abstract void injectDependencies(ActivityComponent buildActivityComponent);

    @LayoutRes
    protected abstract int provideLayoutId();

    protected abstract void setUpView(View view);

    public interface CallBack {

        void onFragmentAttached();

        void onFragmentDetached(String tag);

    }
}
