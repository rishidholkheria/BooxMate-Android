package com.booxapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.booxapp.ExBuyFragment
import com.booxapp.ExBookMarkFragment
import com.booxapp.ExPostFragment
import java.util.ArrayList

class ExViewPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    public override fun getItem(position: Int): Fragment {
        return ExViewPageAdapter.fragmentList[position]
    }

    public override fun getCount(): Int {
        return ExViewPageAdapter.fragmentList.size
    }

    public override fun getPageTitle(position: Int): CharSequence? {
        return ExViewPageAdapter.fragmentTitle[position]
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