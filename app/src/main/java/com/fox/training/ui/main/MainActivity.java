package com.fox.training.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.fox.training.R;
import com.fox.training.intentAndViewPager.viewpager.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout mTabLayout = findViewById(R.id.tl);
        ViewPager mViewPager = findViewById(R.id.viewPager);

        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

    }

}
