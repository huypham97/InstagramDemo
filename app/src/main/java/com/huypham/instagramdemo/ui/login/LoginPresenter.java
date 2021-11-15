package com.huypham.instagramdemo.ui.login;

import android.util.Log;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.data.model.User;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BasePresenter;
import com.huypham.instagramdemo.utils.common.Validator;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<Object, V> implements LoginMvpPresenter<V> {

    private UserRepository userRepository;

    @Inject
    public LoginPresenter(SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable,
                          UserRepository userRepository) {
        super(schedulerProvider, compositeDisposable);
        this.userRepository = userRepository;
    }

    @Override
    public void onLogin(String email, String password) {
        if (email == null || email.isEmpty()) {
            Log.d("TEST", "onLogin: " + getMvpView());
            getMvpView().showMessage(R.string.email_field_empty);
            return;
        }

        if (!Validator.isEmailValid(email)) {
            getMvpView().showMessage(R.string.email_field_invalid);
            return;
        }

        if (password == null || password.isEmpty()) {
            getMvpView().showMessage(R.string.password_field_empty);
            return;
        }

        if (!Validator.isPasswordValid(password)) {
            getMvpView().showMessage(R.string.password_field_small_length);
            return;
        }

        getMvpView().showLoading();
        compositeDisposable.add(
                userRepository.doLoginUser(email, password)
                        .observeOn(schedulerProvider.ui())
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(new Consumer<User>() {
                            @Override
                            public void accept(User user) throws Throwable {
                                userRepository.saveCurrentUser(user);
                                getMvpView().hideLoading();
                                getMvpView().openMainActivity();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                handleNetworkError(throwable);
                                getMvpView().hideLoading();
                            }
                        })
        );
    }
}
