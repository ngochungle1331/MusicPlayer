package com.fox.training;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fox.training.fragment.LibraryFragment;
import com.fox.training.fragment.LoveFragment;
import com.fox.training.fragment.TopMusicFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private final String[] PAGES_TITLE = new String[]{"TOP MUSIC", "YÊU THÍCH ❤", "THƯ VIỆN"};

    private final Fragment[] PAGES = new Fragment[]{new TopMusicFragment(), new LoveFragment(), new LibraryFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout mTabLayout = findViewById(R.id.tl);
        ViewPager mViewPager = findViewById(R.id.viewPager);

        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return PAGES_TITLE[position];
        }
    }


}
