package com.fox.training.ui.homemusic

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fox.training.ui.homemusic.topmusic.TopMusicFragment
import com.fox.training.ui.homemusic.library.LibraryFragment
import com.fox.training.ui.homemusic.favorite.FavoriteFragment
import com.fox.training.util.AppConstants

class HomeMusicAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pagesTitle = arrayOf(
        AppConstants.TITLE_TOP_MUSIC,
        AppConstants.TITLE_LIBRARY, AppConstants.TITLE_LOVE_MUSIC
    )
    private val pageFragments = arrayOf(
        TopMusicFragment(),
        LibraryFragment(), FavoriteFragment()
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
