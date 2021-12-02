package com.huypham.instagramdemo.di.module;

import android.util.Pair;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.data.repository.PhotoRepository;
import com.huypham.instagramdemo.data.repository.PostRepository;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.di.TempDirectory;
import com.huypham.instagramdemo.ui.base.BaseFragment;
import com.huypham.instagramdemo.ui.home.HomeViewModel;
import com.huypham.instagramdemo.ui.home.posts.PostAdapter;
import com.huypham.instagramdemo.ui.main.MainSharedViewModel;
import com.huypham.instagramdemo.ui.photo.PhotoViewModel;
import com.huypham.instagramdemo.utils.ViewModelProviderFactory;
import com.huypham.instagramdemo.utils.common.FileHelper;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;
import com.mindorks.paracamera.Camera;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.processors.PublishProcessor;

@Module
public class FragmentModule {

    private BaseFragment<?> fragment;

    public FragmentModule(BaseFragment<?> fragment) {
        this.fragment = fragment;
    }

    @Provides
    HomeViewModel provideHomeViewModel(SchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable,
                                       NetworkUtils networkUtils,
                                       UserRepository userRepository,
                                       PostRepository postRepository) {
        Supplier<HomeViewModel> supplier = () -> new HomeViewModel(
                schedulerProvider,
                compositeDisposable,
                networkUtils,
                userRepository,
                postRepository,
                new ArrayList<>(),
                PublishProcessor.create());
        ViewModelProviderFactory<HomeViewModel> factory = new ViewModelProviderFactory<>(HomeViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(HomeViewModel.class);
    }

    @Provides
    PhotoViewModel providePhotoViewModel(SchedulerProvider schedulerProvider,
                                         CompositeDisposable compositeDisposable,
                                         UserRepository userRepository,
                                         PhotoRepository photoRepository,
                                         PostRepository postRepository,
                                         NetworkUtils networkUtils,
                                         @TempDirectory File directory,
                                         FileHelper fileHelper) {
        Supplier<PhotoViewModel> supplier = () -> new PhotoViewModel(
                schedulerProvider,
                compositeDisposable,
                networkUtils,
                userRepository,
                postRepository,
                photoRepository,
                directory,
                fileHelper);
        ViewModelProviderFactory<PhotoViewModel> factory = new ViewModelProviderFactory<>(PhotoViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(PhotoViewModel.class);
    }

    @Provides
    PostAdapter providePostAdapter() {
        return new PostAdapter(fragment.getLifecycle(), new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(fragment.getContext());
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
                .build(fragment);
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
        return new ViewModelProvider(fragment.getActivity(), factory).get(MainSharedViewModel.class);
    }

}
