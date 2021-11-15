package com.huypham.instagramdemo.ui.splash;

import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BasePresenter;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<Object, V> implements SplashMvpPresenter<V> {

    private UserRepository userRepository;

    @Inject
    public SplashPresenter(SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable,
                           UserRepository userRepository) {
        super(schedulerProvider, compositeDisposable);
        this.userRepository = userRepository;
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        if (userRepository.getCurrentUser() != null) {
            getMvpView().openMainActivity();
        } else {
            getMvpView().openLoginActivity();
        }
    }
}
