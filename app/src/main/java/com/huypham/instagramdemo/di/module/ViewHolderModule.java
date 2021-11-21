package com.huypham.instagramdemo.di.module;

import com.huypham.instagramdemo.di.scope.ViewHolderScope;
import com.huypham.instagramdemo.ui.base.BaseItemViewHolder;

import androidx.lifecycle.LifecycleRegistry;
import dagger.Module;
import dagger.Provides;

@Module
public class ViewHolderModule {

    private BaseItemViewHolder<?, ?> viewHolder;

    public ViewHolderModule(BaseItemViewHolder<?, ?> viewHolder) {
        this.viewHolder = viewHolder;
    }

    @Provides
    @ViewHolderScope
    LifecycleRegistry provideLifecycleRegistry() {
        return new LifecycleRegistry(viewHolder);
    }
}
