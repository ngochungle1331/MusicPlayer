package com.fox.training.intentAndViewPager.viewpager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.training.R;
import com.fox.training.recyclerview.adapters.TopMusicAdapter;
import com.fox.training.recyclerview.data.network.response.Music;

import java.util.ArrayList;

public class TopMusicFragment extends Fragment {

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

        TopMusicAdapter mMusicAdapter = new TopMusicAdapter(getContext(), mListMusic);
        mTopMusicRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mTopMusicRecyclerView.setAdapter(mMusicAdapter);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}