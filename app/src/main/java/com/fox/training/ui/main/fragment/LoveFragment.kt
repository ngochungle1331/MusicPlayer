package com.fox.training.ui.main.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fox.training.R
import androidx.recyclerview.widget.RecyclerView
import com.fox.training.data.network.response.Music
import com.fox.training.ui.main.adapter.LoveMusicAdapter
import androidx.recyclerview.widget.GridLayoutManager
import java.util.ArrayList

class LoveFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mRootView = inflater.inflate(R.layout.fragment_love, container, false)
        val mLoveMusicRecyclerView = mRootView.findViewById<RecyclerView>(R.id.rvLove)
        val mListMusic = ArrayList<Music>()
        mListMusic.add(
            Music(
                R.drawable.img_peashooter,
                getString(R.string.peashooter_song_name), getString(R.string.peashooter_song_author)
            )
        )
        mListMusic.add(
            Music(
                R.drawable.img_sunflower,
                getString(R.string.sunflower_song_name), getString(R.string.sunflower_song_author)
            )
        )
        val mMusicAdapter = LoveMusicAdapter(context!!, mListMusic)
        mLoveMusicRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mLoveMusicRecyclerView.adapter = mMusicAdapter
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}