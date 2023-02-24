package com.fox.training.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.fox.training.R
import com.fox.training.ui.main.adapter.MainAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val tabLayout = findViewById<TabLayout>(R.id.tlMainActivity)
        val viewPager = findViewById<ViewPager>(R.id.viewPagerMainActivity)
        viewPager.adapter = MainAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

    }
}