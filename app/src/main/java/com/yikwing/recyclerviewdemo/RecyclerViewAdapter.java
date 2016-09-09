package com.yikwing.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * com.yikwing.recyclerviewdemo
 * Created by yikwing on 2016/9/6.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemHolder> implements View.OnClickListener {



    private final Context context;
    private List<String> mDatas;


    public RecyclerViewAdapter(Context context, List<String> datas) {
        this.mDatas = datas;
        this.context = context;

    }

//    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
//
//    //define interface
//    public static interface OnRecyclerViewItemClickListener {
//        void onItemClick(View view, String data);
//    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //创建条目的回调函数
        View root = LayoutInflater.from(context).inflate(R.layout.item_view, null);
        root.setOnClickListener(this);
        return new ItemHolder(root);
    }


    //绑定数据
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        holder.mText.setText(mDatas.get(position));
        holder.itemView.setTag(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }

        return 0;
    }

    @Override
    public void onClick(View v) {
        /*if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }*/

        MyEvent myEvent = new MyEvent((String)v.getTag());
        EventBus.getDefault().post(myEvent);

    }

//    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
//        this.mOnItemClickListener = listener;
//    }

    public class ItemHolder extends RecyclerView.ViewHolder {


        private final TextView mText;

        public ItemHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);
        }

    }


}