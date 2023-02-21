package com.fox.training.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.training.R;
import com.fox.training.data.network.response.Music;

import java.util.ArrayList;
import java.util.List;

public class LoveMusicAdapter extends RecyclerView.Adapter<LoveMusicAdapter.LoveMusicViewHolder> {

    Context mContext;
    List<Music> mMusicArrayList;

    public LoveMusicAdapter(Context mContext, ArrayList<Music> mMusicArrayList) {
        this.mContext = mContext;
        this.mMusicArrayList = mMusicArrayList;
    }

    @NonNull
    @Override
    public LoveMusicAdapter.LoveMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.love_item_card, parent, false);
        return new LoveMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoveMusicAdapter.LoveMusicViewHolder holder, int position) {
        holder.ivSongImage.setImageResource(mMusicArrayList.get(position).getSongImage());
        holder.tvSongName.setText(mMusicArrayList.get(position).getSongName());
        holder.tvSongAuthor.setText(mMusicArrayList.get(position).getSongAuthor());
    }

    @Override
    public int getItemCount() {
        return mMusicArrayList.size();
    }

    public static class LoveMusicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSongImage;
        TextView tvSongName;
        TextView tvSongAuthor;
        CardView cvTopMusic;

        public LoveMusicViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSongImage = itemView.findViewById(R.id.imgSong);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvSongAuthor = itemView.findViewById(R.id.tvAuthor);
            cvTopMusic = itemView.findViewById(R.id.cvTopMusic);
        }
    }
}
