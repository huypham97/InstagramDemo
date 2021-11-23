package com.huypham.instagramdemo.ui.photo;

import com.huypham.instagramdemo.data.repository.PhotoRepository;
import com.huypham.instagramdemo.data.repository.PostRepository;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BaseViewModel;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.io.File;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class PhotoViewModel extends BaseViewModel {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private PhotoRepository photoRepository;
    private File directory;

    public PhotoViewModel(SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable,
                          NetworkUtils networkUtils) {
        super(schedulerProvider, compositeDisposable, networkUtils);
    }

    @Override
    protected void onCreate() {

    }
}
