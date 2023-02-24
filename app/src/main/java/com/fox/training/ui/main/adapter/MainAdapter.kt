package com.fox.training.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fox.training.ui.main.fragment.TopMusicFragment
import com.fox.training.ui.main.fragment.LibraryFragment
import com.fox.training.ui.main.fragment.LoveFragment
import com.fox.training.util.AppConstants

class MainAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pagesTitle = arrayOf(
        AppConstants.TITLE_TOP_MUSIC,
        AppConstants.TITLE_LOVE_MUSIC, AppConstants.TITLE_LIBRARY
    )
    private val pageFragments = arrayOf(
        TopMusicFragment(),
        LoveFragment(), LibraryFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pageFragments[position]
    }

    override fun getCount(): Int {
        return pageFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return pagesTitle[position]
    }
}
