package com.fox.training.recyclerview.adapters;

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
import com.fox.training.recyclerview.data.network.response.Music;

import java.util.ArrayList;
import java.util.List;

public class TopMusicAdapter extends RecyclerView.Adapter<TopMusicAdapter.TopMusicViewHolder> {

    Context mContext;
    List<Music> mMusicArrayList;

    public TopMusicAdapter(Context context, ArrayList<Music> musicArrayList) {
        this.mContext = context;
        this.mMusicArrayList = musicArrayList;
    }

    @NonNull
    @Override
    public TopMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.top_music_item_card,
                parent, false);
        return new TopMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopMusicViewHolder holder, int position) {
        holder.ivSongImage.setImageResource(mMusicArrayList.get(position).getSongImage());
        holder.tvSongName.setText(mMusicArrayList.get(position).getSongName());
        holder.tvSongAuthor.setText(mMusicArrayList.get(position).getSongAuthor());

    }

    @Override
    public int getItemCount() {
        return mMusicArrayList.size();
    }

    public static class TopMusicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSongImage;
        TextView tvSongName;
        TextView tvSongAuthor;
        CardView cvTopMusic;

        public TopMusicViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSongImage = itemView.findViewById(R.id.imgSong);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvSongAuthor = itemView.findViewById(R.id.tvAuthor);
            cvTopMusic = itemView.findViewById(R.id.cvTopMusic);
        }
    }
}
