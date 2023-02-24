package com.fox.training.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.fox.training.R
import com.fox.training.databinding.ActivityMainBinding
import com.fox.training.ui.main.adapter.MainAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabLayoutMainActivity = binding.tlMainActivity
        val viewPagerMainActivity = binding.viewPagerMainActivity
        viewPagerMainActivity.adapter = MainAdapter(supportFragmentManager)
        tabLayoutMainActivity.setupWithViewPager(viewPagerMainActivity)

    }
}