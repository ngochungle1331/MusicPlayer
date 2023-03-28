package com.fox.training.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fox.training.ui.home.top.TopMusicFragment
import com.fox.training.ui.home.library.LibraryFragment
import com.fox.training.ui.home.favorite.FavoriteFragment
import com.fox.training.util.AppConstants

class HomeMusicAdapter(fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {
    private val pagesTitle = arrayOf(
        AppConstants.TITLE_TOP_MUSIC,
        AppConstants.TITLE_LIBRARY,
        AppConstants.TITLE_FAVORITE_MUSIC
    )
    private val pageFragments = arrayOf(
        TopMusicFragment(),
        LibraryFragment(),
        FavoriteFragment()
    )

    override fun getItem(position: Int): Fragment = pageFragments[position]

    override fun getCount(): Int = pageFragments.size

    override fun getPageTitle(position: Int): CharSequence = pagesTitle[position]

}
