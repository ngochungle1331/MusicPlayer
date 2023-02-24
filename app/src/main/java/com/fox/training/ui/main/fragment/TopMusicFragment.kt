package com.fox.training.ui.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.FragmentTopmusicBinding
import com.fox.training.ui.main.adapter.TopMusicAdapter


class TopMusicFragment : Fragment() {

    private var musicList = mutableListOf<Music>()
    private lateinit var binding: FragmentTopmusicBinding
    private lateinit var viewModel: MusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopmusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MusicViewModel::class.java)
        setupViews()
        setData()
    }

    private fun setupViews() = binding.run {
        recycleViewTopMusic.run {
            layoutManager = LinearLayoutManager(context)
            adapter = TopMusicAdapter(musicList) {

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData() {
        viewModel.listMusicLiveData.observe(viewLifecycleOwner) {
            musicList.run {
                clear()
                addAll(it)
            }
            binding.recycleViewTopMusic.adapter?.notifyDataSetChanged()
        }
        viewModel.getTopMusic()
    }
}