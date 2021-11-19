package com.huypham.instagramdemo.di.module;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BaseActivity;
import com.huypham.instagramdemo.ui.splash.SplashViewModel;
import com.huypham.instagramdemo.utils.ViewModelProviderFactory;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;


import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.processors.PublishProcessor;

@Module
public class ActivityModule {
    private BaseActivity<?> activity;

    public ActivityModule(BaseActivity<?> activity) {
        this.activity = activity;
    }

    @Provides
    SplashViewModel provideSplashViewModel(SchedulerProvider schedulerProvider,
                                           CompositeDisposable compositeDisposable,
                                           NetworkUtils networkUtils,
                                           UserRepository userRepository) {
        Supplier<SplashViewModel> supplier = () -> new SplashViewModel(schedulerProvider, compositeDisposable, networkUtils, userRepository);
        ViewModelProviderFactory<SplashViewModel> factory = new ViewModelProviderFactory<>(SplashViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(SplashViewModel.class);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    PublishProcessor<Pair<String, String>> providePagination() {
        return PublishProcessor.create();
    }
}
