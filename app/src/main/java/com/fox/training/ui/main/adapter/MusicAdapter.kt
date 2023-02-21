package com.fox.training.ui.main.adapter

import android.content.Context

import com.fox.training.data.network.response.Music
import androidx.recyclerview.widget.RecyclerView
import com.fox.training.ui.main.adapter.MusicAdapter.TopMusicViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.fox.training.R
import android.widget.TextView
import androidx.cardview.widget.CardView
import java.util.ArrayList

class MusicAdapter(var mContext: Context, musicArrayList: ArrayList<Music>) :
    RecyclerView.Adapter<TopMusicViewHolder>() {
    var mMusicArrayList: List<Music>

    init {
        mMusicArrayList = musicArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMusicViewHolder {
        val view: View
        view = LayoutInflater.from(mContext).inflate(
            R.layout.top_music_item_card,
            parent, false
        )
        return TopMusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopMusicViewHolder, position: Int) {
        holder.ivSongImage.setImageResource(mMusicArrayList[position].songImage)
        holder.tvSongName.text = mMusicArrayList[position].songName
        holder.tvSongAuthor.text = mMusicArrayList[position].songAuthor
    }

    override fun getItemCount(): Int {
        return mMusicArrayList.size
    }

    class TopMusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivSongImage: ImageView
        var tvSongName: TextView
        var tvSongAuthor: TextView
        var cvTopMusic: CardView

        init {
            ivSongImage = itemView.findViewById(R.id.imgSong)
            tvSongName = itemView.findViewById(R.id.tvSongName)
            tvSongAuthor = itemView.findViewById(R.id.tvAuthor)
            cvTopMusic = itemView.findViewById(R.id.cvTopMusic)
        }
    }
}