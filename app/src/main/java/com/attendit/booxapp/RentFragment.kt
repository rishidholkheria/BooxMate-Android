package com.attendit.booxapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceNavigationView
import com.luseen.spacenavigation.SpaceOnClickListener
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class RentFragment : Fragment() {
    var navigationView: SpaceNavigationView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_rent, container, false)
        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(RentCartFragment())
        fragmentList.add(RentBooksFragment())
        loadFragment(RentBooksFragment())
        val coordinatorLayout = v.findViewById<View>(R.id.rent_page) as CoordinatorLayout
        navigationView = v.findViewById(R.id.space)
        navigationView!!.initWithSaveInstanceState(savedInstanceState)
        navigationView!!.addSpaceItem(SpaceItem("", R.drawable.ic_research))
        navigationView!!.addSpaceItem(SpaceItem("", R.drawable.ic_shopping_cart_black_24dp))
        navigationView!!.setCentreButtonSelectable(true)
        navigationView!!.setSpaceOnClickListener(object : SpaceOnClickListener {
            override fun onCentreButtonClick() {
                loadFragment(RentSellBooks())
                navigationView!!.setCentreButtonSelectable(true)
            }

            override fun onItemClick(itemIndex: Int, itemName: String) {
                var fr: Fragment? = null
                when (itemIndex) {
                    0 -> {
                        fr = RentBooksFragment()
                        loadFragment(fr)
                    }
                    1 -> {
                        fr = RentCartFragment()
                        loadFragment(fr)
                    }
                }
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {
                //   Toast.makeText(MainPage.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        })
        return v
    }

    private fun loadFragment(f: Fragment?): Boolean {
        if (f != null) {
            fragmentManager!!.beginTransaction().replace(R.id.main_container, f).commit()
            return true
        }
        return false
    }
}