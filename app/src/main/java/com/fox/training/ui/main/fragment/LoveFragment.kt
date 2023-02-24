package com.fox.training.ui.main.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fox.training.data.network.response.Music
import androidx.recyclerview.widget.LinearLayoutManager
import com.fox.training.data.network.response.DataResult
import com.fox.training.data.source.remote.RemoteDataSource
import com.fox.training.databinding.FragmentLoveBinding
import com.fox.training.ui.main.adapter.TopMusicAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoveFragment : Fragment() {

    private var musicList = mutableListOf<Music>()
    private lateinit var binding: FragmentLoveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoveBinding.inflate(inflater, container, false)
        return binding.root
    }

}