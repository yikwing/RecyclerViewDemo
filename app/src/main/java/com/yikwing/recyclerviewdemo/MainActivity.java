package com.yikwing.recyclerviewdemo;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private List<String> datas = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        initView();
        EventBus.getDefault().register(this);
        initData();
        initEvent();
    }

    private void initEvent() {

        ItemTouchHelper.Callback mCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

                    /**
                     * @param recyclerView
                     * @param viewHolder 拖动的ViewHolder
                     * @param target 目标位置的ViewHolder
                     * @return
                     */
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                        int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                        if (fromPosition < toPosition) {
                            //分别把中间所有的item的位置重新交换
                            for (int i = fromPosition; i < toPosition; i++) {
                                Collections.swap(datas, i, i + 1);
                            }
                        } else {
                            for (int i = fromPosition; i > toPosition; i--) {
                                Collections.swap(datas, i, i - 1);
                            }
                        }
                        mAdapter.notifyItemMoved(fromPosition, toPosition);
                        //返回true表示执行拖动
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        datas.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }

                    //float dX 在水平方向的偏移总量
                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                            //滑动时改变Item的透明度
                            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                            viewHolder.itemView.setAlpha(alpha);
                        }
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    private void initData() {


        for (int i = 0; i < 100; i++) {
            datas.add(String.valueOf(i));
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);


        //设置样式    ---->水平或垂直
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        //设置布局管理器
        mRecyclerView.setLayoutManager(manager);

        //设置数据
        mAdapter = new RecyclerViewAdapter(this, datas);
        mRecyclerView.setAdapter(mAdapter);

        /*mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view , String data){
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });*/


        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(this));


    }

    public void onEventMainThread(MyEvent event) {
        Toast.makeText(this, event.data, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
