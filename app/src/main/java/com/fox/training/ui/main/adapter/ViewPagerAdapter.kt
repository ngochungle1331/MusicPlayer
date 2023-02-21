package com.fox.training.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fox.training.util.AppConstants
import com.fox.training.ui.main.fragment.TopMusicFragment
import com.fox.training.ui.main.fragment.LoveFragment
import com.fox.training.ui.main.fragment.LibraryFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val PAGES_TITLE = arrayOf(
        AppConstants.TITLE_TOP_MUSIC,
        AppConstants.TITLE_LOVE_MUSIC, AppConstants.TITLE_LIBRARY
    )
    private val PAGES = arrayOf(
        TopMusicFragment(),
        LoveFragment(), LibraryFragment()
    )

    override fun getItem(position: Int): Fragment {
        return PAGES[position]
    }

    override fun getCount(): Int {
        return PAGES.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return PAGES_TITLE[position]
    }
}