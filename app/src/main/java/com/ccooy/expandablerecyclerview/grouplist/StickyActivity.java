package com.ccooy.expandablerecyclerview.grouplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ccooy.expandablerecyclerview.R;
import com.ccooy.expandablerecyclerview.grouplist.adapter.NoFooterAdapter;
import com.ccooy.expandablerecyclerview.grouplist.other.GroupModel;
import com.ccooy.expandablerecyclerview.grouplist.widget.BaseViewHolder;
import com.ccooy.expandablerecyclerview.grouplist.widget.GroupedRecyclerViewAdapter;
import com.ccooy.expandablerecyclerview.grouplist.widget.StickyHeaderLayout;

/**
 * 没有组头的分组列表。
 */
public class StickyActivity extends AppCompatActivity {

    private TextView tvTitle;
    private RecyclerView rvList;
    private StickyHeaderLayout stickyLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_list);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        stickyLayout = (StickyHeaderLayout) findViewById(R.id.sticky_layout);

        tvTitle.setText(R.string.sticky_list);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        NoFooterAdapter adapter = new NoFooterAdapter(this, GroupModel.getGroups(10, 5));
        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                      int groupPosition) {
                Toast.makeText(StickyActivity.this, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
                Log.e("eee", adapter.toString() + "  " + holder.toString());
            }
        });

        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {
                Toast.makeText(StickyActivity.this, "子项：groupPosition = " + groupPosition
                                + ", childPosition = " + childPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        rvList.setAdapter(adapter);

        //设置是否吸顶。
//        stickyLayout.setSticky(true);
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, StickyActivity.class);
        context.startActivity(intent);
    }
}
