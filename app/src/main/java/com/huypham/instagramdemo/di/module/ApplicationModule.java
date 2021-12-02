package com.huypham.instagramdemo.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.huypham.instagramdemo.BuildConfig;
import com.huypham.instagramdemo.data.local.prefs.AppUserPreferences;
import com.huypham.instagramdemo.data.local.prefs.UserPreferences;
import com.huypham.instagramdemo.data.remote.NetworkService;
import com.huypham.instagramdemo.data.remote.Networking;
import com.huypham.instagramdemo.di.ApplicationContext;
import com.huypham.instagramdemo.di.TempDirectory;
import com.huypham.instagramdemo.utils.common.FileHelper;
import com.huypham.instagramdemo.utils.common.FileUtils;
import com.huypham.instagramdemo.utils.display.ScreenResourceProvider;
import com.huypham.instagramdemo.utils.display.ScreenUtils;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.RxSchedulerProvider;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new RxSchedulerProvider();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences("bootcamp-instagram-project-prefs", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    UserPreferences provideUserPreferences(AppUserPreferences appUserPreferences) {
        return appUserPreferences;
    }

    @Provides
    @Singleton
    NetworkService provideNetworkService() {
        return Networking.create(
                BuildConfig.API_KEY,
                BuildConfig.BASE_URL,
                application.getCacheDir(),
                Long.valueOf(10 * 1024 * 1024));
    }

    @Provides
    @Singleton
    NetworkUtils provideNetworkUtils() {
        return new NetworkUtils();
    }

    @Provides
    @Singleton
    @TempDirectory
    File provideTempDirectory() {
        return new FileUtils().getDirectory(application, "temp");
    }

    @Provides
    @Singleton
    ScreenResourceProvider provideScreenResourceProvider() {
        return new ScreenUtils();
    }

    @Provides
    @Singleton
    FileHelper provideFileHelper() {
        return new FileUtils();
    }
}
