package com.example.newsapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity, private val fragments: List<Fragment>) : FragmentStateAdapter(activity) {
    // Returns the number of fragments
    override fun getItemCount(): Int = fragments.size

    // Creates a new fragment for the given position
    override fun createFragment(position: Int): Fragment = fragments[position]
}