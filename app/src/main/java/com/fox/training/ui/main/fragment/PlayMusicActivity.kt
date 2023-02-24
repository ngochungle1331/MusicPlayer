package com.fox.training.ui.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.ActivityPlayMusicBinding
import com.fox.training.ui.main.adapter.TopMusicAdapter
import com.fox.training.util.AppConstants


class PlayMusicActivity : AppCompatActivity() {

    private var musicList = mutableListOf<Music>()
    private lateinit var binding: ActivityPlayMusicBinding
    private lateinit var music: Music
    private lateinit var viewModel: MusicViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        music = intent.getSerializableExtra(AppConstants.intentPutExtraKey) as Music

        setupViews()
        setData(music)

    }

    private fun setupViews() = binding.run {
        recycleviewRelativeSongs.run {
            layoutManager = LinearLayoutManager(context)
            adapter = TopMusicAdapter(musicList){
                setData(it)
                viewModel.getRecommendedMusic(it)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun setData(music: Music) {
        binding.run {
            tvPlayingSongName.text = music.name
            tvPlayingSongAuthor.text = music.artistsNames
            tvSongLength.text = "%02d:%02d".format(music.duration / 60, music.duration % 60)
            Glide.with(this@PlayMusicActivity).load(makeThumbnailBrighter(music))
                .into(imgPlayingSongImage)
        }
        viewModel.listMusicLiveData.observe(this) {
            musicList.run {
                clear()
                addAll(it)
            }
            binding.recycleviewRelativeSongs.adapter?.notifyDataSetChanged()
        }
        viewModel.getRecommendedMusic(music)
    }

    private fun makeThumbnailBrighter(music: Music): String {
        val token = music.thumbnail.split("/")
        val rm = token[3]
        return music.thumbnail.substring(
            0,
            music.thumbnail.indexOf(rm)
        ) + music.thumbnail.substring(music.thumbnail.indexOf(rm) + rm.length + 1)
    }

}