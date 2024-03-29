package com.booxapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class ViewPagerAdapter constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    public override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    public override fun getCount(): Int {
        return fragmentList.size
    }

    public override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitle[position]
    }

    companion object {
        private val fragmentList: ArrayList<Fragment> = ArrayList()
        private val fragmentTitle: ArrayList<String> = ArrayList()
        @JvmStatic
        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitle.add(title)
        }
    }

    init {
        fragmentList.clear()
        fragmentTitle.clear()
    }
}