package com.huypham.instagramdemo.di.component;

import com.huypham.instagramdemo.di.scope.FragmentScope;
import com.huypham.instagramdemo.di.module.FragmentModule;
import com.huypham.instagramdemo.ui.home.HomeFragment;
import com.huypham.instagramdemo.ui.photo.PhotoFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(HomeFragment fragment);

    void inject(PhotoFragment fragment);
}
