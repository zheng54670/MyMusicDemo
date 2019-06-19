package com.example.mymusicdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.mymusicdemo.R;
import com.example.mymusicdemo.activities.PlayMusicActivity;

public class MusicLinearAdapter extends RecyclerView.Adapter<MusicLinearAdapter.ViewHolder> {


    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalculationRvHeight;

    public MusicLinearAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        mRv = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_linear_music, viewGroup, false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        setRecyclerVIewHeight();

        //引入网络图片
        Glide.with(mContext)
                .load("http://res.lgdsunday.club/poster-1.png")
                .into(viewHolder.ivIcon);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayMusicActivity.class);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 8;
    }

    /**
     * 1、获取ItemView的高度
     * 2、获取ItemView的数量
     * 3、ItemView * ItemViewNum = RecyclerView的高度
     */
    private void setRecyclerVIewHeight() {

        if (isCalculationRvHeight || mRv ==null){
            return;
        }

        isCalculationRvHeight = true;

        //获取ItemView的高度
        RecyclerView.LayoutParams itemViewLayoutParams = (RecyclerView.LayoutParams) mItemView.getLayoutParams();

        //获取ItemView的数量
        int itemCount = getItemCount();

        //ItemView * ItemViewNum = RecyclerView的高度
        int recyclerViewHeight = itemViewLayoutParams.height * itemCount;

        //设置RecyclerView高度
        LinearLayout.LayoutParams recyclerViewLayoutParams = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        recyclerViewLayoutParams.height = recyclerViewHeight;
        mRv.setLayoutParams(recyclerViewLayoutParams);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView ivIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
