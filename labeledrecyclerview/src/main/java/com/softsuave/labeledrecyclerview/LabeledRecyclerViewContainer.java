package com.softsuave.labeledrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class LabeledRecyclerViewContainer extends RelativeLayout implements LabeledRecyclerView.LabelChangeListener {

    private LabeledRecyclerView mRecyclerView;
    private RelativeLayout mLabelLayout;

    public LabeledRecyclerViewContainer(Context context) {
        this(context, null);
        initViews();
    }

    public LabeledRecyclerViewContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabeledRecyclerViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        addView(LayoutInflater.from(getContext()).inflate(R.layout.labeled_recycler_view, this, false));
        mRecyclerView = findViewById(R.id.rvLabeled);
        mLabelLayout = findViewById(R.id.rlLabelContainer);
        mRecyclerView.setLabelChangeListener(this);
    }

    public RelativeLayout getLabelLayout() {
        return mLabelLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onLabelChange(View view) {
        mLabelLayout.removeAllViews();
        mLabelLayout.addView(view);
    }
}
