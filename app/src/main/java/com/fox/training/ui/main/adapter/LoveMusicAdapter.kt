package com.fox.training.ui.main.adapter

import android.content.Context
import com.fox.training.data.network.response.Music
import androidx.recyclerview.widget.RecyclerView
import com.fox.training.ui.main.adapter.LoveMusicAdapter.LoveMusicViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.fox.training.R
import android.widget.TextView
import androidx.cardview.widget.CardView
import java.util.ArrayList

class LoveMusicAdapter(var mContext: Context, mMusicArrayList: ArrayList<Music>) :
    RecyclerView.Adapter<LoveMusicViewHolder>() {
    var mMusicArrayList: List<Music>

    init {
        this.mMusicArrayList = mMusicArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoveMusicViewHolder {
        val view: View
        view = LayoutInflater.from(mContext).inflate(R.layout.love_item_card, parent, false)
        return LoveMusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoveMusicViewHolder, position: Int) {
        holder.ivSongImage.setImageResource(mMusicArrayList[position].songImage)
        holder.tvSongName.text = mMusicArrayList[position].songName
        holder.tvSongAuthor.text = mMusicArrayList[position].songAuthor
    }

    override fun getItemCount(): Int {
        return mMusicArrayList.size
    }

    class LoveMusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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