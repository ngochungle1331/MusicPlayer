package com.fox.training.ui.play

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.MusicItemCardBinding

class PlayMusicAdapter(
    private var musicList: List<Music>,
    private var onClick: (Music) -> Unit,
) :
    RecyclerView.Adapter<PlayMusicAdapter.PlayMusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayMusicViewHolder =
        PlayMusicViewHolder(
            MusicItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClick.invoke(musicList[adapterPosition])
                }
            }
        }

    override fun onBindViewHolder(
        holder: PlayMusicViewHolder,
        position: Int
    ) {
        holder.onBind(musicList[position])
    }

    override fun getItemCount(): Int = musicList.size

    class PlayMusicViewHolder(private val binding: MusicItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(music: Music) {
            binding.run {
                music.run {
                    tvPosition.text = order?.toInt().toString()
                    tvPosition.setTextColor(Color.WHITE)
                    tvSongName.text = name
                    tvArtists.text = artistsNames
                    Glide.with(itemView.context).load(music.thumbnail).centerCrop()
                        .placeholder(R.drawable.logo).into(imgSong)
                }
            }
        }
    }
}

