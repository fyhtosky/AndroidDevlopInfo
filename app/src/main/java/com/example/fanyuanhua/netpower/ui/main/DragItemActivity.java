package com.example.fanyuanhua.netpower.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.adapter.DragAdapter;
import com.example.fanyuanhua.netpower.adapter.drag.DragItemCallBack;
import com.example.fanyuanhua.netpower.adapter.drag.RecycleCallBack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * RecycleView  拖动排序的
 */
public class DragItemActivity extends AppCompatActivity implements RecycleCallBack {
    private RecyclerView mRecyclerView;
    private DragAdapter mAdapter;
    private ArrayList<String> mList;
    private ItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_item);
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("" + i);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DragAdapter(this, mList);
        mItemTouchHelper = new ItemTouchHelper(new DragItemCallBack(this));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void itemOnClick(int position, View view) {
        if (view.getId() == R.id.del) {
            mList.remove(position);
            mAdapter.setData(mList);
            mAdapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(DragItemActivity.this, "当前点击的是" + position, Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMove(int from, int to) {
        synchronized (this) {
            if (from > to) {
                int count = from - to;
                for (int i = 0; i < count; i++) {
                    Collections.swap(mList, from - i, from - i - 1);
                }
            }
            if (from < to) {
                int count = to - from;
                for (int i = 0; i < count; i++) {
                    Collections.swap(mList, from + i, from + i + 1);
                }
            }
            mAdapter.setData(mList);
            mAdapter.notifyItemMoved(from, to);
            mAdapter.show.clear();
            mAdapter.show.put(to, to);
        }
    }
}
