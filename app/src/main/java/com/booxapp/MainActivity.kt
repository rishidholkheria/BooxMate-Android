package com.booxapp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.booxapp.adapter.ViewPagerAdapter
import com.booxapp.adapter.ViewPagerAdapter.Companion.addFragment
import com.booxapp.data.Prefs.getStringPrefs
import com.booxapp.data.Prefs.putStringPrefs
import com.booxapp.databinding.MainActivityBinding
import com.booxapp.exchange.ExchangeFragment
import com.booxapp.model.UserModel
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

        binding.menuIcon!!.setOnClickListener {}

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

//    private fun sendToFirebase(current_user: UserModel) {
//        val userid = FirebaseDatabase.getInstance().getReference("users").push().key.toString()
//        val id = FirebaseAuth.getInstance().currentUser!!.uid
//        current_user.id = id
//        val user_ref = FirebaseDatabase.getInstance().getReference("users").child(userid)
//        user_ref.setValue(current_user)
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        addFragment(SellFragment(), "Sell")
        addFragment(PurchaseFragment(), "Purchase")
        addFragment(ExchangeFragment(), "Exchange")
        viewPager!!.adapter = viewPagerAdapter
    }

}