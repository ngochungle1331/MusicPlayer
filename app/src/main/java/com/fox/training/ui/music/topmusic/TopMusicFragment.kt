package com.fox.training.ui.music.topmusic

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
import com.fox.training.databinding.FragmentTopmusicBinding
import com.fox.training.services.MusicService
import com.fox.training.ui.playmusic.PlayMusicActivity
import com.fox.training.util.AppConstants


class TopMusicFragment : Fragment() {

    private var listTopMusic = mutableListOf<Music>()
    private lateinit var binding: FragmentTopmusicBinding
    private lateinit var viewModel: TopMusicViewModel
    private var musicService = MusicService()

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MusicService.MyBinder
            musicService = myBinder.getMusicService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
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
        binding = FragmentTopmusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TopMusicViewModel::class.java]
        setupViews()
        setData()
    }

    private fun setupViews() = binding.run {
        recycleViewTopMusic.run {
            layoutManager = LinearLayoutManager(context)
            adapter = TopMusicAdapter(listTopMusic) {
                startMusic(it)
                musicService.musicList = listTopMusic
                context.startService(Intent(context, PlayMusicActivity::class.java))
            }
            val divider = DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL)
            this.addItemDecoration(divider)
        }
    }

    private fun setData() {
        viewModel.listMusicLiveData.observe(viewLifecycleOwner) {
            listTopMusic.run {
                clear()
                addAll(it)
            }
            binding.recycleViewTopMusic.adapter?.notifyDataSetChanged()
        }
        viewModel.getTopMusic()
    }

    private fun startMusic(music: Music) {
        val intent = Intent(context, PlayMusicActivity::class.java)
        intent.putExtra(AppConstants.SEND_MUSIC, music as java.io.Serializable)
        startActivity(intent)
    }
}