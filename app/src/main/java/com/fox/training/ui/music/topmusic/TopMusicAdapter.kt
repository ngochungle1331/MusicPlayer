package com.fox.training.ui.music.topmusic

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.MusicItemCardBinding
import com.fox.training.ui.music.topmusic.TopMusicAdapter.MusicViewHolder

class TopMusicAdapter(
    private var musicList: List<Music>,
    private var onClick: (Music) -> Unit
) :
    RecyclerView.Adapter<MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder =
        MusicViewHolder(
            MusicItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClick(musicList[adapterPosition])
                }
            }
        }

    override fun onBindViewHolder(
        holder: MusicViewHolder, position: Int
    ) {
        holder.onBind(musicList[position])
    }

    override fun getItemCount(): Int = musicList.size

    class MusicViewHolder(private val binding: MusicItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(music: Music) {
            binding.run {
                music.run {
                    tvPosition.text = order.toInt().toString()
                    when (order.toInt()) {
                        1 -> tvPosition.setTextColor(Color.RED)
                        2 -> tvPosition.setTextColor(Color.GREEN)
                        3 -> tvPosition.setTextColor(Color.MAGENTA)
                        else -> tvPosition.setTextColor(Color.WHITE)
                    }
                    tvSongName.text = name
                    tvAuthor.text = artistsNames
                    Glide.with(itemView.context).load(music.thumbnail).centerCrop()
                        .placeholder(R.drawable.logo).into(imgSong)
                }
            }
        }
    }
}