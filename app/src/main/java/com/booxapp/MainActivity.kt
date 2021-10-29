package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.booxapp.BookBuzz.BookBuzz
import com.booxapp.adapter.ViewPagerAdapter
import com.booxapp.adapter.ViewPagerAdapter.Companion.addFragment
import com.booxapp.data.Prefs.getStringPrefs
import com.booxapp.data.Prefs.putStringPrefs
import com.booxapp.databinding.MainActivityBinding
import com.booxapp.exchange.ExchangeFragment
import com.booxapp.model.UserModel
import com.booxapp.purchase.BookmarkedBooks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private val mAuthStateListener: AuthStateListener? = null

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myProfileBtn!!.setOnClickListener {
            val i = Intent(this, MyProfile::class.java)
            startActivity(i)
            Toast.makeText(this@MainActivity, "My profile!", Toast.LENGTH_LONG).show()
        }

        binding.logoutBtn!!.setOnClickListener {
            mFirebaseAuth.signOut()
            val i = Intent(this, SignIn::class.java)
            startActivity(i)
            Toast.makeText(this@MainActivity, "Logged Out Successfully!", Toast.LENGTH_LONG).show()
        }

        setupViewPager(binding.myViewPager)
        binding.tablayout!!.setupWithViewPager(binding.myViewPager)
    }

    private val userDetails: Unit
        private get() {
            val id = FirebaseAuth.getInstance().currentUser!!.uid

            val ref = FirebaseDatabase.getInstance().getReference("user")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    putStringPrefs(applicationContext, "user_loc", user!!.loc)
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        addFragment(PurchaseFragment(), "Purchase")
        addFragment(SellFragment(), "Sell")
        addFragment(ExchangeFragment(), "Exchange")
        viewPager!!.adapter = viewPagerAdapter
    }

}