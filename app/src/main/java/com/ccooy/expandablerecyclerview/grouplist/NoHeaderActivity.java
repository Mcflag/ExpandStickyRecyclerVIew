package com.ccooy.expandablerecyclerview.grouplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ccooy.expandablerecyclerview.R;
import com.ccooy.expandablerecyclerview.grouplist.adapter.NoHeaderAdapter;
import com.ccooy.expandablerecyclerview.grouplist.other.GroupModel;
import com.ccooy.expandablerecyclerview.grouplist.widget.BaseViewHolder;
import com.ccooy.expandablerecyclerview.grouplist.widget.GroupedRecyclerViewAdapter;

/**
 * 没有组头的分组列表。
 */
public class NoHeaderActivity extends AppCompatActivity {

    private TextView tvTitle;
    private RecyclerView rvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        rvList = (RecyclerView) findViewById(R.id.rv_list);

        tvTitle.setText(R.string.no_header);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        NoHeaderAdapter adapter = new NoHeaderAdapter(this, GroupModel.getGroups(10, 5));
        adapter.setOnFooterClickListener(new GroupedRecyclerViewAdapter.OnFooterClickListener() {
            @Override
            public void onFooterClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                Toast.makeText(NoHeaderActivity.this, "组尾：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {
                Toast.makeText(NoHeaderActivity.this, "子项：groupPosition = " + groupPosition
                                + ", childPosition = " + childPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        rvList.setAdapter(adapter);
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, NoHeaderActivity.class);
        context.startActivity(intent);
    }
}
