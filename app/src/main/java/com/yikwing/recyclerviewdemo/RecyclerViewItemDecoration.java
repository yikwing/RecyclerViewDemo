package com.yikwing.recyclerviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * com.yikwing.recyclerviewdemo
 * Created by yikwing on 2016/9/6.
 */

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {


    public static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private final Drawable drawable;

    public RecyclerViewItemDecoration(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        drawable = typedArray.getDrawable(0);
        typedArray.recycle();

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getChildAt(0).getLeft();
        int right = parent.getChildAt(0).getRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom();
            int bottom = top + drawable.getIntrinsicHeight();

            drawable.setBounds(left, top, right, bottom);
            drawable.draw(c);


        }


    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
