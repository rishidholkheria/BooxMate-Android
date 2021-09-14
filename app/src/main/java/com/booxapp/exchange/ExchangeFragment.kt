package com.booxapp.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
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

        binding.exViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.exBottomNav.setItemSelected(R.id.exbooks, true)
                    }
                    1 -> {
                        binding.exBottomNav.setItemSelected(R.id.post, true)
                    }
                    2 -> {
                        binding.exBottomNav.setItemSelected(R.id.bookmark, true)
                    }
                }
                super.onPageSelected(position)
            }
        })

        binding.exBottomNav.setOnItemSelectedListener(object :
            ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                when (id) {
                    R.id.exbooks -> {
                        binding.exViewPager.setCurrentItem(0)
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

        return binding.root
    }

    private fun setupViewPager(viewPager: ViewPager2?) {
        val exViewPageAdapter = ExViewPageAdapter(this)
        ExViewPageAdapter.addFragment(SellFragment(), "Sell")
        ExViewPageAdapter.addFragment(PurchaseFragment(), "Purchase")
        ExViewPageAdapter.addFragment(ExchangeFragment(), "Exchange")
        viewPager!!.adapter = exViewPageAdapter
    }
}