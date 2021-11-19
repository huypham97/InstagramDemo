package com.huypham.instagramdemo.ui.login.signUp;

import com.huypham.instagramdemo.R;
import com.huypham.instagramdemo.data.model.User;
import com.huypham.instagramdemo.data.repository.UserRepository;
import com.huypham.instagramdemo.ui.base.BaseViewModel;
import com.huypham.instagramdemo.utils.common.Validator;
import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import java.util.Collections;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class SignUpViewModel extends BaseViewModel {

    private final UserRepository userRepository;

    public SignUpViewModel(SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable,
                           NetworkUtils networkUtils,
                           UserRepository userRepository) {
        super(schedulerProvider, compositeDisposable, networkUtils);
        this.userRepository = userRepository;
    }

    MutableLiveData<Map<String, String>> launchMain = new MutableLiveData<>();

    MutableLiveData<String> emailField = new MutableLiveData<>();
    MutableLiveData<String> passwordField = new MutableLiveData<>();
    MutableLiveData<String> nameField = new MutableLiveData<>();

    MutableLiveData<Boolean> loggingIn = new MutableLiveData<>();

    @Override
    protected void onCreate() {

    }

    public void onEmailChanged(String email) {
        emailField.postValue(email);
    }

    public void onPasswordChanged(String password) {
        passwordField.postValue(password);
    }

    public void onNameChanged(String name) {
        nameField.postValue(name);
    }

    public void onSignUp() {
        String email = emailField.getValue();
        String password = passwordField.getValue();
        String name = nameField.getValue();

        if (name == null || name.isEmpty()) {
            messageStringId.postValue(R.string.name_field_empty);
            return;
        }

        if (email == null || email.isEmpty()) {
            messageStringId.postValue(R.string.email_field_empty);
            return;
        }

        if (!Validator.isEmailValid(email)) {
            messageStringId.postValue(R.string.email_field_invalid);
            return;
        }

        if (password == null || password.isEmpty()) {
            messageStringId.postValue(R.string.password_field_empty);
            return;
        }

        if (!Validator.isPasswordValid(password)) {
            messageStringId.postValue(R.string.password_field_small_length);
            return;
        }

        loggingIn.postValue(true);
        compositeDisposable.addAll(
                userRepository.doSignUpUser(email, password, name)
                        .observeOn(schedulerProvider.ui())
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(new Consumer<User>() {
                            @Override
                            public void accept(User user) throws Throwable {
                                userRepository.saveCurrentUser(user);
                                loggingIn.postValue(false);
                                launchMain.postValue(Collections.emptyMap());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                handleNetworkError(throwable);
                                loggingIn.postValue(false);
                            }
                        })
        );
    }
}
