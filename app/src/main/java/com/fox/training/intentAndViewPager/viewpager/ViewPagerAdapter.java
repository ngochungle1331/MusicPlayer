package com.fox.training.intentAndViewPager.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fox.training.recyclerview.utils.AppConstants;
import com.fox.training.intentAndViewPager.viewpager.fragment.LibraryFragment;
import com.fox.training.intentAndViewPager.viewpager.fragment.LoveFragment;
import com.fox.training.intentAndViewPager.viewpager.fragment.TopMusicFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final String[] PAGES_TITLE = new String[]{AppConstants.TITLE_TOP_MUSIC,
            AppConstants.TITLE_LOVE_MUSIC, AppConstants.TITLE_LIBRARY};

    private final Fragment[] PAGES = new Fragment[]{new TopMusicFragment(),
            new LoveFragment(), new LibraryFragment()};

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
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
