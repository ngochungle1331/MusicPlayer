package com.fox.training.ui.homemusic.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.MusicItemCardBinding

class LibraryAdapter(
    private var musicList: List<Music>,
    private var onClick: (Music) -> Unit
) :
    RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder =
        LibraryViewHolder(
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

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.onBind(musicList[position], position)
    }

    override fun getItemCount(): Int = musicList.size

    class LibraryViewHolder(private val binding: MusicItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(music: Music, position: Int) {
            binding.run {
                val index = position + 1
                music.run {
                    tvPosition.text = index.toString()
                    tvSongName.text = name
                    tvArtists.text = artistsNames
                    Glide.with(itemView.context).load(music.thumbnail).centerCrop()
                        .placeholder(R.drawable.logo).into(imgSong)
                }
            }
        }
    }
}