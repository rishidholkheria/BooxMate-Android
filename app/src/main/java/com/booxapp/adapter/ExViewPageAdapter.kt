package com.booxapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.booxapp.ExBuyFragment
import com.booxapp.ExBookMarkFragment
import com.booxapp.ExPostFragment
import java.util.ArrayList

class ExViewPageAdapter(fa: Fragment) : FragmentStateAdapter(fa) {

    companion object {
        private val fragmentList: ArrayList<Fragment> = ArrayList()
        private val fragmentTitle: ArrayList<String> = ArrayList()

        @JvmStatic
        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitle.add(title)
        }

        private const val NUM_PAGES = 3
    }

    init {
        fragmentList.clear()
        fragmentTitle.clear()
    }

    override fun getItemCount(): Int = Companion.NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ExBuyFragment()
            }
            1 -> {
                ExPostFragment()
            }
            2 -> {
                ExBookMarkFragment()
            }
            else -> ExBuyFragment()
        }
    }
}