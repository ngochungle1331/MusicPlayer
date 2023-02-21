package com.fox.training.ui.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fox.training.R;
import com.fox.training.data.network.response.Music;
import com.fox.training.ui.main.adapter.MusicAdapter;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_topmusic, container, false);
        RecyclerView mTopMusicRecyclerView = mRootView.findViewById(R.id.rvTopMusic);
        ArrayList<Music> mListMusic = new ArrayList<>();

        mListMusic.add(new Music(R.drawable.img_peashooter,
                getString(R.string.peashooter_song_name), getString(R.string.peashooter_song_author)));
        mListMusic.add(new Music(R.drawable.img_sunflower,
                getString(R.string.sunflower_song_name), getString(R.string.sunflower_song_author)));
        mListMusic.add(new Music(R.drawable.img_cherrybomb,
                getString(R.string.cherry_bomb_song_name), getString(R.string.cherry_bomb_song_author)));
        mListMusic.add(new Music(R.drawable.img_wallnut,
                getString(R.string.wall_nut_song_name), getString(R.string.wall_nut_song_author)));

        MusicAdapter mMusicAdapter = new MusicAdapter(getContext(), mListMusic);
        mTopMusicRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mTopMusicRecyclerView.setAdapter(mMusicAdapter);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}