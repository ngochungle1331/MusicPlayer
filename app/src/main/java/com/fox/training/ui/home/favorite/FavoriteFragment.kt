package com.fox.training.ui.home.favorite

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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.FragmentFavoriteBinding
import com.fox.training.service.MusicService
import com.fox.training.ui.play.PlayMusicActivity
import com.fox.training.util.AppConstants
import kotlinx.coroutines.launch
import java.io.Serializable

class FavoriteFragment : Fragment() {
    private var listFavoriteMusic = mutableListOf<Music>()
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private var musicService = MusicService()

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MusicService.MyBinder
            musicService = myBinder.getMusicService()
        }

        override fun onServiceDisconnected(name: ComponentName?) = Unit
    }

    override fun onStart() {
        super.onStart()
        Intent(requireContext(), MusicService::class.java).also { intent ->
            requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        setupView()
        setData()
    }

    private fun setupView() = binding.run {
        recyclerViewFavorite.run {
            layoutManager = LinearLayoutManager(context)
            adapter = FavoriteAdapter(listFavoriteMusic) {
                startMusic(it)
                musicService.musicList = listFavoriteMusic
            }
        }
    }

    private fun setData() {
        lifecycleScope.launch {
            viewModel.listFavoriteMusic.observe(viewLifecycleOwner) {
                listFavoriteMusic.run {
                    clear()
                    addAll(it)
                }
                binding.recyclerViewFavorite.adapter?.notifyDataSetChanged()
            }
            viewModel.getListMusic(requireContext())
        }
    }

    private fun startMusic(music: Music) {
        val intent = Intent(context, PlayMusicActivity::class.java)
        intent.putExtra(AppConstants.SEND_MUSIC, music as Serializable)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unbindService(serviceConnection)
    }

}