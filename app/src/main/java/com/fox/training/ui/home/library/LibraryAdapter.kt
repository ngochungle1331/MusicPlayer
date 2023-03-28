package com.fox.training.ui.home.library

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.MusicItemCardBinding
import com.fox.training.ui.home.library.LibraryAdapter.LibraryViewHolder

class LibraryAdapter(
    private var musicList: List<Music>,
    private var onClick: (Music) -> Unit
) : RecyclerView.Adapter<LibraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder =
        LibraryViewHolder(
            MusicItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClick(musicList[adapterPosition])
                }
            }
        }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.onBind(musicList[position])
    }

    override fun getItemCount(): Int = musicList.size

    class LibraryViewHolder(private var binding: MusicItemCardBinding) :
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
                    imgSong.setImageResource(R.drawable.logo)
                }
            }
        }
    }
}