package com.ccooy.expandablerecyclerview.decoration;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.ccooy.expandablerecyclerview.R;
import com.ccooy.expandablerecyclerview.decoration.widget.BaseViewHolder;
import com.ccooy.expandablerecyclerview.decoration.widget.GroupedRecyclerViewAdapter;

import java.util.ArrayList;

public class ExpandableAdapter extends GroupedRecyclerViewAdapter {

    private ArrayList<ExpandableGroupEntity> mGroups;

    public ExpandableAdapter(Context context, ArrayList<ExpandableGroupEntity> groups) {
        super(context);
        mGroups = groups;
    }

    public void setGroups(ArrayList<ExpandableGroupEntity> groups){
        this.mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //如果当前组收起，就直接返回0，否则才返回子项数。这是实现列表展开和收起的关键。
        if (!isExpand(groupPosition)) {
            return 0;
        }
        ArrayList<ChildEntity> children = mGroups.get(groupPosition).getChildren();
        return children == null ? 0 : children.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.adapter_expandable_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.adapter_footer;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.adapter_child;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        ExpandableGroupEntity entity = mGroups.get(groupPosition);
        holder.setText(R.id.tv_expandable_header, entity.getHeader().getTime());
        holder.setText(R.id.tv_count, "收款笔数：" + entity.getHeader().getCount());
        holder.setText(R.id.tv_amount, "收款金额：" + entity.getHeader().getAmount());
        holder.setText(R.id.tv_discount_num, "优惠笔数：" + entity.getHeader().getDiscount_num());
        holder.setText(R.id.tv_discount_amount, "优惠金额：" + entity.getHeader().getDiscount_amount());
        ImageView ivState = holder.get(R.id.iv_state);
        if(entity.isExpand()){
            holder.setVisible(R.id.ll_trade_discount, View.VISIBLE);
            ivState.setRotation(90);
        } else {
            holder.setVisible(R.id.ll_trade_discount, View.GONE);
            ivState.setRotation(0);
        }
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
        ExpandableGroupEntity entity = mGroups.get(groupPosition);
        if(entity.isExpand() && entity.getIsAll()){
            holder.setText(R.id.tv_footer, "已经到底了老板");
            holder.setVisible(R.id.tv_footer, View.VISIBLE);
        } else {
            holder.setVisible(R.id.tv_footer, View.GONE);
        }
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        ChildEntity entity = mGroups.get(groupPosition).getChildren().get(childPosition);
        holder.setText(R.id.tv_type, entity.getType());
        holder.setText(R.id.tv_money, entity.getMoney());
    }

    /**
     * 判断当前组是否展开
     *
     * @param groupPosition
     * @return
     */
    public boolean isExpand(int groupPosition) {
        ExpandableGroupEntity entity = mGroups.get(groupPosition);
        return entity.isExpand();
    }

    /**
     * 展开一个组
     *
     * @param groupPosition
     */
    public void expandGroup(int groupPosition) {
        expandGroup(groupPosition, false);
    }

    /**
     * 展开一个组
     *
     * @param groupPosition
     * @param animate
     */
    public void expandGroup(int groupPosition, boolean animate) {
        if (animate) {
            notifyHeaderChanged(groupPosition);
            notifyFooterChanged(groupPosition);
            notifyChildrenInserted(groupPosition);
        } else {
            notifyDataChanged();
        }
    }

    /**
     * 收起一个组
     *
     * @param groupPosition
     */
    public void collapseGroup(int groupPosition) {
        collapseGroup(groupPosition, false);
    }

    /**
     * 收起一个组
     *
     * @param groupPosition
     * @param animate
     */
    public void collapseGroup(int groupPosition, boolean animate) {
        if (animate) {
            notifyHeaderChanged(groupPosition);
            notifyFooterChanged(groupPosition);
            notifyChildrenRemoved(groupPosition);
        } else {
            notifyDataChanged();
        }
    }
}
