package com.huypham.instagramdemo.ui.base;

import com.huypham.instagramdemo.utils.network.NetworkUtils;
import com.huypham.instagramdemo.utils.rx.SchedulerProvider;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseItemViewModel<T> extends BaseViewModel {

    protected MutableLiveData<T> data = new MutableLiveData<>();

    public BaseItemViewModel(SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable,
                             NetworkUtils networkUtils) {
        super(schedulerProvider, compositeDisposable, networkUtils);
    }

    public void onManualCleared() {
        onCleared();
    }

    public void updateData(T data) {
        this.data.postValue(data);
    }

}
