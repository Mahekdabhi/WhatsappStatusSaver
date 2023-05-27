package com.example.whatsappstatussaver.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsappstatussaver.ui.ImageFragment
import com.example.whatsappstatussaver.ui.SavedFilesFragment
import com.example.whatsappstatussaver.ui.VideoFragment

class PageAdapter(fm: FragmentManager, private val totalTabs: Int) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 1) {
            return VideoFragment()
        } else if (position == 2) return SavedFilesFragment()
        return ImageFragment()
    }

    override fun getCount(): Int {
        return totalTabs
    }
}
