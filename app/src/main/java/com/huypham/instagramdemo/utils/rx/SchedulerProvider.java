package com.huypham.instagramdemo.utils.rx;

import io.reactivex.rxjava3.core.Scheduler;

public interface SchedulerProvider {
    Scheduler io();

    Scheduler ui();
}
