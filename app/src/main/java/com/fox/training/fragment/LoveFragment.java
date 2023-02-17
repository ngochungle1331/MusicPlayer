package com.fox.training.fragment;

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
import com.fox.training.data.model.Music;
import com.fox.training.recyclerview.LoveMusicAdapter;

import java.util.ArrayList;

public class LoveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_love, container, false);
        RecyclerView mLoveMusicRecyclerView = mRootView.findViewById(R.id.rvLove);
        ArrayList<Music> mListMusic = new ArrayList<>();

        mListMusic.add(new Music(R.drawable.img_peashooter,
                "Peashooter Song", "Peashooter"));
        mListMusic.add(new Music(R.drawable.img_sunflower,
                "SunFlower Song", "SunFlower"));

        LoveMusicAdapter mMusicAdapter = new LoveMusicAdapter(getContext(), mListMusic);
        mLoveMusicRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mLoveMusicRecyclerView.setAdapter(mMusicAdapter);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}