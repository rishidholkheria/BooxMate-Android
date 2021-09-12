package com.booxapp.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.booxapp.*
import com.booxapp.adapter.ExViewPageAdapter
import com.booxapp.adapter.ViewPagerAdapter
import com.booxapp.databinding.FragmentExchangeBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class ExchangeFragment : Fragment() {

    lateinit var binding: FragmentExchangeBinding

    private val TAG = "ExchangeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExchangeBinding.inflate(inflater, container, false)

        binding.exBottomNav.setItemSelected(
            com.booxapp.R.id.exbooks,
            true
        );

        setupViewPager(binding.exViewPager)

        binding.exBottomNav.setOnItemSelectedListener(object :
            ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                when (id) {
                    R.id.exbooks -> {
                        binding.exViewPager.setCurrentItem(1)
                    }
                    R.id.post -> {
                        binding.exViewPager.setCurrentItem(1)
                    }
                    R.id.bookmark -> {
                        binding.exViewPager.setCurrentItem(2)
                    }
                }
            }
        })

        binding.exViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when {
                    position == 0 -> {
                        binding.exBottomNav.setItemSelected(R.id.exbooks, true)
                    }
                    position == 1 -> {
                        binding.exBottomNav.setItemSelected(R.id.post, true)
                    }
                    position == 2 -> {
                        binding.exBottomNav.setItemSelected(R.id.bookmark, true)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })

        return binding.root
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val viewPagerAdapter = ExViewPageAdapter(parentFragmentManager)
        ExViewPageAdapter.addFragment(ExBuyFragment(), "Buy")
        ExViewPageAdapter.addFragment(ExPostFragment(), "Post")
        ExViewPageAdapter.addFragment(ExBookMarkFragment(), "Bookmark")
        viewPager!!.adapter = viewPagerAdapter
    }
}