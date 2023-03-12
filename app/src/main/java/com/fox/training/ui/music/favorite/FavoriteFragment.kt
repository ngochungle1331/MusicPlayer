package com.fox.training.ui.music.favorite

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.FragmentFavoriteBinding
import com.fox.training.ui.music.topmusic.TopMusicAdapter
import com.fox.training.services.MusicService
import com.fox.training.ui.playmusic.PlayMusicActivity
import com.fox.training.util.AppConstants

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

}