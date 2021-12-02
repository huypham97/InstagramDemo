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
import com.huypham.instagramdemo.ui.login.LoginViewModel;
import com.huypham.instagramdemo.ui.login.signUp.SignUpViewModel;
import com.huypham.instagramdemo.ui.main.MainSharedViewModel;
import com.huypham.instagramdemo.ui.main.MainViewModel;
import com.huypham.instagramdemo.ui.splash.SplashViewModel;
import com.huypham.instagramdemo.utils.ViewModelProviderFactory;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;
import com.mindorks.paracamera.Camera;


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
        Supplier<SplashViewModel> supplier = () -> new SplashViewModel(
                schedulerProvider,
                compositeDisposable,
                networkUtils,
                userRepository);
        ViewModelProviderFactory<SplashViewModel> factory = new ViewModelProviderFactory<>(SplashViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(SplashViewModel.class);
    }

    @Provides
    LoginViewModel provideLoginViewModel(SchedulerProvider schedulerProvider,
                                         CompositeDisposable compositeDisposable,
                                         NetworkUtils networkUtils,
                                         UserRepository userRepository) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(
                schedulerProvider,
                compositeDisposable,
                networkUtils,
                userRepository);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(LoginViewModel.class);
    }

    @Provides
    SignUpViewModel provideSignUpViewModel(SchedulerProvider schedulerProvider,
                                           CompositeDisposable compositeDisposable,
                                           NetworkUtils networkUtils,
                                           UserRepository userRepository) {
        Supplier<SignUpViewModel> supplier = () -> new SignUpViewModel(
                schedulerProvider,
                compositeDisposable,
                networkUtils,
                userRepository);
        ViewModelProviderFactory<SignUpViewModel> factory = new ViewModelProviderFactory<>(SignUpViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(SignUpViewModel.class);
    }

    @Provides
    MainViewModel provideMainViewModel(SchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable,
                                       NetworkUtils networkUtils) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(
                schedulerProvider,
                compositeDisposable,
                networkUtils);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    @Provides
    MainSharedViewModel provideMainSharedViewModel(SchedulerProvider schedulerProvider,
                                                   CompositeDisposable compositeDisposable,
                                                   NetworkUtils networkUtils) {
        Supplier<MainSharedViewModel> supplier = () -> new MainSharedViewModel(
                schedulerProvider,
                compositeDisposable,
                networkUtils);
        ViewModelProviderFactory<MainSharedViewModel> factory = new ViewModelProviderFactory<>(MainSharedViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainSharedViewModel.class);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    PublishProcessor<Pair<String, String>> providePagination() {
        return PublishProcessor.create();
    }

    @Provides
    Camera provideCamera() {
        return new Camera.Builder()
                .resetToCorrectOrientation(true)
                .setTakePhotoRequestCode(Camera.REQUEST_TAKE_PHOTO)
                .setDirectory("instaClone")
                .setName("instaClick ${System.currentTimeMillis()}")
                .setCompression(75)
                .setImageFormat(Camera.IMAGE_JPG)
                .setImageHeight(100)
                .build(activity);
    }
}
