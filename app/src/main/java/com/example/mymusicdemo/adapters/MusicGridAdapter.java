package com.example.mymusicdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymusicdemo.R;
import com.example.mymusicdemo.activities.AlbumListActivity;
import com.example.mymusicdemo.models.AlbumModel;

import java.util.List;

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.ViewHolder> {

    private Context mContext;
    private List<AlbumModel> mDataSource;

    public MusicGridAdapter(Context mContext,List<AlbumModel> dataSource) {
        this.mContext = mContext;
        this.mDataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_grid_music,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final AlbumModel albumModel = mDataSource.get(i);

        //引入网络图片
        Glide.with(mContext)
                .load(albumModel.getPoster())
                .into(viewHolder.rvIcon);

        viewHolder.mTvPlayNum.setText(albumModel.getPlayNum());
        viewHolder.mTvName.setText(albumModel.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumListActivity.class);
                intent.putExtra(AlbumListActivity.ALBUM_ID,albumModel.getAlbumId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView rvIcon;
        View itemView;
        TextView mTvPlayNum,mTvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            rvIcon = itemView.findViewById(R.id.rv_icon);
            mTvPlayNum = itemView.findViewById(R.id.tv_play_num);
            mTvName = itemView.findViewById(R.id.tv_name);
        }
    }

}
