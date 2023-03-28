package com.fox.training.ui.home.favorite

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.MusicItemCardBinding
import com.fox.training.ui.home.favorite.FavoriteAdapter.FavoriteViewHolder

class FavoriteAdapter(
    private var musicList: List<Music>,
    private var onClick: (Music) -> Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
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

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.onBind(musicList[position])
    }

    override fun getItemCount(): Int = musicList.size

    class FavoriteViewHolder(private val binding: MusicItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(music: Music) {
            binding.run {
                music.run {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        val index = adapterPosition + 1
                        tvPosition.text = index.toString()
                        this.position = index
                    }
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