package com.huypham.instagramdemo.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huypham.instagramdemo.InstagramApplication;
import com.huypham.instagramdemo.di.component.DaggerFragmentComponent;
import com.huypham.instagramdemo.di.component.FragmentComponent;
import com.huypham.instagramdemo.di.module.FragmentModule;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {

    @Inject
    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies(buildFragmentComponent());
        super.onCreate(savedInstanceState);
        setupObserver();
        viewModel.onCreate();
    }

    private FragmentComponent buildFragmentComponent() {
        return DaggerFragmentComponent
                .builder()
                .applicationComponent(((InstagramApplication) getContext().getApplicationContext()).getComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(provideLayoutId(), container, false);
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
            public void onChanged(Integer messageId) {
                showMessage(messageId);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    public void goBack() {
        if (getActivity() instanceof BaseActivity<?>) {
            ((BaseActivity<?>) getActivity()).goBack();
        }
    }

    protected abstract int provideLayoutId();

    protected abstract void injectDependencies(FragmentComponent fragmentComponent);

    protected abstract void setupView(View view);
}
