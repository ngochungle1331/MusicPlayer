package com.fox.training.ui.music.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    private var musicList = mutableListOf<Music>()
    private lateinit var binding: FragmentLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

}