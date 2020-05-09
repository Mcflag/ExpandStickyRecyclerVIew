package com.ccooy.expandablerecyclerview.stockdemo;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.ccooy.expandablerecyclerview.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.oushangfeng.pinnedsectionitemdecoration.utils.FullSpanUtil;

import java.util.List;

public class StockAdapter extends RecyclerViewAdapter<StockEntity.StockInfo, StickyHeadEntity<StockEntity.StockInfo>> implements CompoundButton.OnCheckedChangeListener {

    public final static int TYPE_HEAD = 4;

    public StockAdapter(List<StickyHeadEntity<StockEntity.StockInfo>> data) {
        super(data);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, TYPE_STICKY_HEAD);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        FullSpanUtil.onViewAttachedToWindow(holder, this, TYPE_STICKY_HEAD);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                return R.layout.item_stock_header;
            case TYPE_STICKY_HEAD:
                return R.layout.item_stock_sticky_head;
            case TYPE_DATA:
                return R.layout.item_stock_data;
            case TYPE_SMALL_STICKY_HEAD_WITH_DATA:
                return R.layout.item_stock_small_sticky_data;
        }
        return 0;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int viewType, int position, StockEntity.StockInfo item) {
        int type = holder.getItemViewType();
        switch (type) {

            case TYPE_STICKY_HEAD:

                CheckBox checkBox = holder.getCheckBox(R.id.checkbox);
                checkBox.setTag(position);
                checkBox.setOnCheckedChangeListener(this);
                checkBox.setChecked(item.check);

                holder.setText(R.id.tv_stock_name, item.stickyHeadName);
                if(item.check){
                    holder.setViewInvisible(R.id.tv_stock_more, true);
                }else{
                    holder.setViewGone(R.id.tv_stock_more, true);
                }

                break;

            case TYPE_DATA:
                setData(holder, item);
                break;
            case TYPE_SMALL_STICKY_HEAD_WITH_DATA:
                setData(holder, item);
                holder.setText(R.id.tv_stock_name, item.stickyHeadName);
                break;

        }
    }

    private void setData(RecyclerViewHolder holder, StockEntity.StockInfo item) {
        final String stockNameAndCode = item.stock_name + "\n" + item.stock_code;
        SpannableStringBuilder ssb = new SpannableStringBuilder(stockNameAndCode);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#a4a4a7")), item.stock_name.length(), stockNameAndCode.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new AbsoluteSizeSpan(dip2px(holder.itemView.getContext(), 13)), item.stock_name.length(), stockNameAndCode.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.setText(R.id.tv_stock_name_code, ssb).setText(R.id.tv_current_price, item.current_price)
                .setText(R.id.tv_rate, (item.rate < 0 ? String.format("%.2f", item.rate) : "+" + String.format("%.2f", item.rate)) + "%");
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = (Integer) buttonView.getTag();
        mData.get(pos).getData().check = isChecked;
    }
}
