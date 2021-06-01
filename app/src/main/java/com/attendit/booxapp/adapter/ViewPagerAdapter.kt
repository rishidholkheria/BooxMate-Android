package com.attendit.booxapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class ViewPagerAdapter constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    public override fun getItem(position: Int): Fragment {
        return fragmentlist.get(position)
    }

    public override fun getCount(): Int {
        return fragmentlist.size
    }

    public override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitle.get(position)
    }

    companion object {
        private val fragmentlist: ArrayList<Fragment> = ArrayList()
        private val fragmentTitle: ArrayList<String> = ArrayList()
        @JvmStatic
        fun addFragment(fragment: Fragment, title: String) {
            fragmentlist.add(fragment)
            fragmentTitle.add(title)
        }
    }

    init {
        fragmentlist.clear()
        fragmentTitle.clear()
    }
}