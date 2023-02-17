package com.fox.training.fragment;

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
import com.fox.training.data.model.Music;
import com.fox.training.recyclerview.TopMusicAdapter;

import java.util.ArrayList;


public class LibraryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_topmusic, container, false);
        RecyclerView mTopMusicRecyclerView = mRootView.findViewById(R.id.rvTopMusic);
        ArrayList<Music> mListMusic = new ArrayList<>();

        mListMusic.add(new Music(R.drawable.img_peashooter,
                "Peashooter Song", "Peashooter"));
        mListMusic.add(new Music(R.drawable.img_sunflower,
                "SunFlower Song", "SunFlower"));
        mListMusic.add(new Music(R.drawable.img_cherrybomb,
                "Cherry Bomb Song", "Cherry Bomb"));
        mListMusic.add(new Music(R.drawable.img_wallnut,
                "Wall Nut Song", "Wall Nut Bomb"));

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