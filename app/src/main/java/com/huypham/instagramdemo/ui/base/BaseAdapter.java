package com.huypham.instagramdemo.ui.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<T, VH extends BaseItemViewHolder<T, ? extends BaseItemViewModel<T>>> extends RecyclerView.Adapter<VH> {

    protected Lifecycle parentLifecycle;
    protected ArrayList<T> dataList;

    private RecyclerView recyclerView = null;

    public BaseAdapter(Lifecycle parentLifecycle, ArrayList<T> dataList) {
        this.parentLifecycle = parentLifecycle;
        this.dataList = dataList;
        init();
    }

    private void init() {
        parentLifecycle.addObserver(new LifecycleObserver() {

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            void onParentStart() {
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                    for (int i = 0; i <= last; i++) {
                        if (first == i) {
                            for (int j = first; j <= last; j++) {
                                ((BaseItemViewHolder<?, ?>) recyclerView.findViewHolderForAdapterPosition(j)).onStart();
                            }
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            void onParentStop() {
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    ((BaseItemViewHolder<?, ?>) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).onStop();
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            void onParentDestroy() {
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    ((BaseItemViewHolder<?, ?>) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).onDestroy();
                    ((BaseItemViewHolder<?, ?>) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).viewModel.onManualCleared();
                }
            }

        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull @NotNull VH holder) {
        super.onViewAttachedToWindow(holder);
        holder.onStart();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull @NotNull VH holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onStop();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void appendData(List<T> dataList) {
        int oldCount = getItemCount();
        this.dataList.addAll(dataList);
        int currentCount = getItemCount();
        if (oldCount == 0 && currentCount > 0)
            notifyDataSetChanged();
        else if (oldCount >= 1 && oldCount <= currentCount) {
            notifyItemRangeChanged(oldCount - 1, currentCount - oldCount);
        }
    }

    public void updateList(List<T> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }
}
