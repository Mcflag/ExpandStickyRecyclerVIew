package com.ccooy.expandablerecyclerview.decoration.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.ccooy.expandablerecyclerview.decoration.library.State;

public class CommonRefreshHeaderView extends SimpleRefreshHeaderView {

    private PullListener pullListener;

    public CommonRefreshHeaderView(Context context) {
        super(context);
    }

    public CommonRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPullListener(PullListener pullListener) {
        this.pullListener = pullListener;
    }

    @Override
    public void pull() {
        super.pull();
        if(pullListener != null){
            pullListener.pull();
        }
    }

    @Override
    public void reset() {
        super.reset();
        if(pullListener != null){
            pullListener.reset();
        }
    }
}
