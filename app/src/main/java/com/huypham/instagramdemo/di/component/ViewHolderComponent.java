package com.huypham.instagramdemo.di.component;

import com.huypham.instagramdemo.di.module.ViewHolderModule;
import com.huypham.instagramdemo.di.scope.ViewHolderScope;
import com.huypham.instagramdemo.ui.home.posts.PostItemViewHolder;

import dagger.Component;

@ViewHolderScope
@Component(dependencies = ApplicationComponent.class, modules = ViewHolderModule.class)
public interface ViewHolderComponent {

    void inject(PostItemViewHolder viewHolder);
}
