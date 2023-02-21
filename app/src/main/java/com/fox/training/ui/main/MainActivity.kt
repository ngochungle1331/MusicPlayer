package com.fox.training.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fox.training.R
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import com.fox.training.ui.main.adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mTabLayout = findViewById<TabLayout>(R.id.tl)
        val mViewPager = findViewById<ViewPager>(R.id.viewPager)
        mViewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        mTabLayout.setupWithViewPager(mViewPager)
    }
}