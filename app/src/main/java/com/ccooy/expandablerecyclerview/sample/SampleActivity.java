package com.ccooy.expandablerecyclerview.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ccooy.expandablerecyclerview.R;
import com.ccooy.expandablerecyclerview.sample.widget.AnimEndListener;
import com.ccooy.expandablerecyclerview.sample.widget.BaseViewHolder;
import com.ccooy.expandablerecyclerview.sample.widget.DefaultItemAnimator;
import com.ccooy.expandablerecyclerview.sample.widget.GroupedRecyclerViewAdapter;
import com.ccooy.expandablerecyclerview.sample.widget.StickyHeaderLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import static com.ccooy.expandablerecyclerview.sample.GroupModel.getHeader;

/**
 * 可展开、收起的列表。
 */
public class SampleActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private SmartRefreshLayout refreshLayout;
    private StickyHeaderLayout stickyHeaderLayout;
    private FloatingActionButton floatingActionButton;
    private ArrayList<ExpandableGroupEntity> groupList;
    private ArrayList<ExpandableGroupEntity> displayList;
    private ExpandableAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoadGroup = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        rvList = findViewById(R.id.rv_list);
        stickyHeaderLayout = findViewById(R.id.sticky_layout);
        floatingActionButton = findViewById(R.id.fab);
        refreshLayout = findViewById(R.id.refreshLayout);
        linearLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(linearLayoutManager);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setMoveDuration(70);
        animator.setChangeDuration(70);
        animator.setAddDuration(70);
        animator.setRemoveDuration(70);
        animator.setAnimEndListener(new AnimEndListener() {
            @Override
            public void onAnimationEnd() {
                stickyHeaderLayout.updateStickyDelayed();
            }
        });
        rvList.setItemAnimator(animator);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
//                    stickyHeaderLayout.updateStickyDelayed();
//                }
                if (null != linearLayoutManager) {
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
                    int itemHeight = firstVisiableChildView.getHeight();
                    int flag = (position) * itemHeight - firstVisiableChildView.getTop();
                    if (flag >= itemHeight) {
                        floatingActionButton.setVisibility(View.VISIBLE);
                    } else {
                        floatingActionButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (null != linearLayoutManager) {
                    if (isLoadGroup) {
//                        int last = linearLayoutManager.findLastVisibleItemPosition();
//                        int lastGroup = adapter.getGroupPositionForPosition(last);
//                        if(adapter.getGroupCount()-lastGroup <= 5) {
//                            loadMoreTime();
//                            adapter.notifyDataSetChanged();
//                            refreshLayout.finishLoadMore(true);
//                        }
                    } else {
                        int last = linearLayoutManager.findLastVisibleItemPosition();
                        int lastGroup = adapter.getGroupPositionForPosition(last);
                        if(adapter.getChildrenCount(lastGroup)-adapter.getChildPositionForPosition(lastGroup, last)<=5) {
                            loadMoreData(lastGroup);
                            adapter.notifyDataSetChanged();
                            refreshLayout.finishLoadMore(true);
                        }
                    }
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvList.smoothScrollToPosition(0);
            }
        });

        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.finishRefresh(true);
            }
        });

        groupList = new ArrayList<>();
        displayList = new ArrayList<>();
        adapter = new ExpandableAdapter(this, displayList);
        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                      int groupPosition) {
                ExpandableAdapter expandableAdapter = (ExpandableAdapter) adapter;
                int tempPosition = expandableAdapter.getPositionForGroup(groupPosition);
                if (expandableAdapter.isExpand(groupPosition)) {
                    groupList.get(groupPosition).setNeedToLoad(false);
                    groupList.get(groupPosition).setExpand(false);
                    deleteGroupChild(groupPosition);
                    expandableAdapter.collapseGroup(groupPosition, true);
                    stickyHeaderLayout.updateStickyDelayed();
                } else {
                    groupList.get(groupPosition).setExpand(true);
                    groupList.get(groupPosition).setNeedToLoad(true);
                    int count = adapter.getGroupCount() - groupPosition;
                    loadMoreData(groupPosition);
                    adapter.notifyGroupRangeRemoved(groupPosition, count);
                    expandableAdapter.expandGroup(groupPosition, true);
                    smoothMoveToPosition(rvList, tempPosition);
                }
            }
        });
        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {
            }
        });
        rvList.setAdapter(adapter);
        refresh();
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SampleActivity.class);
        context.startActivity(intent);
    }

    private void loadMoreData(int index){
        ArrayList<ChildEntity> temp = groupList.get(index).getChildren();
        ArrayList<ChildEntity> remote = GroupModel.getChild();
        int total = Integer.parseInt(groupList.get(index).getHeader().getCount());
        if(total > temp.size() + remote.size()){
            groupList.get(index).setIsAll(false);
            temp.addAll(remote);
        }else{
            groupList.get(index).setIsAll(true);
            int childrenSize = groupList.get(index).getChildren().size();
            for(int i =0;(i < total - childrenSize) && (i < remote.size()); i++){
                temp.add(remote.get(i));
            }
        }
        groupList.get(index).setChildren(temp);
        copyToDisplay();
    }

    private void loadMoreTime(){
        ArrayList<ExpandableGroupEntity> tempGroups = new ArrayList<>();
        ArrayList<HeadEntity> headers = getHeader();
        for (int i = 0; i < headers.size(); i++) {
            tempGroups.add(new ExpandableGroupEntity(headers.get(i),
                    "", false, new ArrayList<ChildEntity>()));
        }
        groupList.addAll(tempGroups);
        copyToDisplay();
    }

    private void deleteGroupChild(int index){
        groupList.get(index).setChildren(new ArrayList<ChildEntity>());
        copyToDisplay();
    }

    private void refresh(){
        groupList.clear();
        ArrayList<HeadEntity> headers = getHeader();
        for (int i = 0; i < headers.size(); i++) {
            groupList.add(new ExpandableGroupEntity(headers.get(i),
                    "", false, new ArrayList<ChildEntity>()));
        }
        groupList.get(0).setExpand(true);
        loadMoreData(0);
        copyToDisplay();
        adapter.notifyDataSetChanged();
    }

    private void copyToDisplay(){
        ArrayList<ExpandableGroupEntity> tempList = new ArrayList<>();
        boolean onlyOneChildToLoad = true;
        if(groupList.size()>0) {
            for (ExpandableGroupEntity item : groupList){
                if(!onlyOneChildToLoad){
                    isLoadGroup = false;
                    break;
                }else{
                    isLoadGroup = true;
                }
                if(!item.getIsAll() && item.isNeedToLoad()){
                    onlyOneChildToLoad = false;
                }
                tempList.add(item);
            }
        }

        displayList.clear();
        displayList.addAll(tempList);
    }

    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
        }
    }
}
