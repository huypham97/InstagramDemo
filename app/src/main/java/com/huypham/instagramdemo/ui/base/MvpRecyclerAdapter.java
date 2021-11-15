package com.huypham.instagramdemo.ui.base;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class MvpRecyclerAdapter<M, P extends BasePresenter, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    protected final Map<Object, P> presenters;

    public MvpRecyclerAdapter() {
        presenters = new HashMap<>();
    }

    @NonNull
    protected P getPresenter(@NonNull M model) {
        return presenters.get(getModelId(model));
    }

    @Override
    public void onViewRecycled(@NonNull @NotNull VH holder) {
        super.onViewRecycled(holder);
        holder.detachPresenter();
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull @NotNull VH holder) {
        holder.detachPresenter();
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        holder.attachPresenter(getPresenter(getItem(position)));
    }

    @NonNull
    protected abstract P createPresenter(@NonNull M model);

    @NonNull
    protected abstract Object getModelId(@NonNull M model);

    protected abstract M getItem(int position);
}
