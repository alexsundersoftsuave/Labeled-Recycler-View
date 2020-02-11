package com.softsuave.labeledrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class LabeledRecyclerView extends RecyclerView {

    private int currentPosition = -1;
    private Adapter adapter;
    private LabelChangeListener labelChangeListener;

    public LabeledRecyclerView(@NonNull Context context) {
        super(context);
    }

    public LabeledRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LabeledRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLabelChangeListener(LabelChangeListener labelChangeListener) {
        this.labelChangeListener = labelChangeListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (adapter != null && labelChangeListener != null) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
            if (linearLayoutManager != null) {
                int visibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                for (int i = visibleItemPosition; i >= 0; i--) {
                    if (adapter.isLabel(i)) {
                        if (currentPosition != i) {
                            currentPosition = i;
                            ViewHolder viewHolder = adapter.onCreateViewHolder(this, adapter.getItemViewType(i));
                            viewHolder.bindData(i);
                            labelChangeListener.onLabelChange(viewHolder.itemView);
                        }
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        if (adapter != null && !(adapter instanceof Adapter))
            throw new RuntimeException("Use the " + Adapter.class.getName() + " only for this RecyclerView");
        super.setAdapter(adapter);
    }

    public void setAdapter(@Nullable Adapter adapter) {
        this.adapter = adapter;
        super.setAdapter(adapter);
    }

    public abstract static class Adapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {

        public boolean isLabel(int position){
            return false;
        }

        @NonNull
        @Override
        public abstract VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindData(int position) {
            Log.d(ViewHolder.class.getSimpleName(), "bindData with position: " + position);
        }
    }

    public interface LabelChangeListener {
        void onLabelChange(View view);
    }
}
