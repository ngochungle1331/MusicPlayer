package com.fox.training.ui.home.top

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.FragmentTopmusicBinding
import com.fox.training.service.MusicService
import com.fox.training.ui.play.PlayMusicActivity
import com.fox.training.util.AppConstants
import java.io.Serializable


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

        override fun onServiceDisconnected(name: ComponentName?) = Unit
    }

    override fun onStart() {
        super.onStart()
        Intent(requireContext(), MusicService::class.java).also { intent ->
            requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
        recyclerViewTopMusic.run {
            layoutManager = LinearLayoutManager(context)
            adapter = TopMusicAdapter(listTopMusic) {
                startMusic(it)
                musicService.musicList = listTopMusic
            }
            val divider = DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL)
            this.addItemDecoration(divider)
        }

    }

    private fun setData() {
        viewModel.listTopMusic.observe(viewLifecycleOwner) {
            listTopMusic.run {
                clear()
                addAll(it)
            }
            binding.recyclerViewTopMusic.adapter?.notifyDataSetChanged()
        }

        binding.run {
            if (searchView.query.isNullOrEmpty()) {
                viewModel.getTopMusic()
            } else {
                viewModel.searchMusic(searchView.query.toString())
            }
            searchView.run {

                setOnCloseListener {
                    viewModel.getTopMusic()
                    clearFocus()
                    false
                }
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.searchMusic(query)
                        // Unfocus bàn phím
                        clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        // Có thể thực hiện tìm kiếm ngay khi người dùng nhập vào SearchView
                        viewModel.searchMusic(newText)
                        return true
                    }
                })
            }
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