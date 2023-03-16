package com.fox.training.ui.homemusic.library

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.FragmentLibraryBinding
import com.fox.training.service.MusicService
import com.fox.training.ui.playmusic.PlayMusicActivity
import com.fox.training.util.AppConstants

class LibraryFragment : Fragment() {

    private var listLibraryMusic = mutableListOf<Music>()
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var viewModel: LibraryViewModel
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
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LibraryViewModel::class.java]
        setupView()
        setData()
    }

    private fun setupView() = binding.run {
        recyclerViewLibrary.run {
            layoutManager = LinearLayoutManager(context)
            adapter = LibraryAdapter(listLibraryMusic) {
                startMusic(it)
                musicService.musicList = listLibraryMusic
                context.startService(Intent(context, PlayMusicActivity::class.java))
            }
            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            this.addItemDecoration(divider)
        }
    }

    private fun setData() {
        viewModel.listLibraryMusic.observe(viewLifecycleOwner) {
            listLibraryMusic.run {
                clear()
                addAll(it)
            }
            binding.recyclerViewLibrary.adapter?.notifyDataSetChanged()
        }
    }

    private fun startMusic(music: Music) {
        val intent = Intent(context, PlayMusicActivity::class.java)
        intent.putExtra(AppConstants.SEND_MUSIC, music as java.io.Serializable)
        startActivity(intent)
    }

}