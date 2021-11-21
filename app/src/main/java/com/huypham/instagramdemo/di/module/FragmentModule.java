package com.huypham.instagramdemo.di.module;

import android.util.Pair;

import com.huypham.instagramdemo.data.model.Post;
import com.huypham.instagramdemo.data.repository.PostRepository;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BaseFragment;
import com.huypham.instagramdemo.ui.home.HomeViewModel;
import com.huypham.instagramdemo.ui.home.posts.PostAdapter;
import com.huypham.instagramdemo.utils.ViewModelProviderFactory;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

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
    PostAdapter providePostAdapter() {
        return new PostAdapter(fragment.getLifecycle(), new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(fragment.getContext());
    }
}
