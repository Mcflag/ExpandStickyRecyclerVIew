package com.ccooy.expandablerecyclerview.decoration.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.ccooy.expandablerecyclerview.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import pl.droidsonroids.gif.GifImageView;

public class RefreshLoadingHeader extends LinearLayout implements RefreshHeader {

    private TextView textView;
//    private GifImageView gifImageView;

    public RefreshLoadingHeader(Context context) {
        super(context);
        initView(context);
    }

    public RefreshLoadingHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public RefreshLoadingHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.header_refresh_loading, this);
        textView = view.findViewById(R.id.tv_header_refresh_loading);
//        gifImageView = view.findViewById(R.id.gif_header_refresh_loading);
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int maxDragHeight) {
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        return 300;//延迟xxx毫秒之后再弹回
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                textView.setText("下拉开始刷新");
//                gifImageView.setVisibility(GONE);//隐藏动画
                break;
            case Refreshing:
                textView.setText("正在刷新");
//                gifImageView.setVisibility(VISIBLE);//显示加载动画
                break;
            case ReleaseToRefresh:
                textView.setText("松开立即刷新");
                break;
        }
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int maxDragHeight) {
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
    }
}