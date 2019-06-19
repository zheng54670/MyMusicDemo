package com.example.mymusicdemo.views;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public GridSpaceItemDecoration(int space,RecyclerView parent) {
        mSpace = space;
        getRecyclerViewOffsets(parent);
    }


    /**
     * @param outRect Item的矩形边界
     * @param view    ItemView
     * @param parent  RecyclerView
     * @param state   RecyclerView 状态
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = mSpace;
//        判断当前Item是不是每一行的第一个Item
//        if (parent.getChildLayoutPosition(view) % 3 == 0) {
//            outRect.left = 0;
//        }

//        View margin
//        margin 为正，则View会距离边界产生距离
//        margin 为负，则View会超出边界产生距离

    }

    private void getRecyclerViewOffsets(RecyclerView parent){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) parent.getLayoutParams();
        layoutParams.leftMargin = -mSpace;
        parent.setLayoutParams(layoutParams);
    }
}
