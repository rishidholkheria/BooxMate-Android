package com.attendit.booxapp

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.attendit.booxapp.adapter.ViewPagerAdapter
import com.attendit.booxapp.adapter.ViewPagerAdapter.Companion.addFragment
import com.attendit.booxapp.data.Prefs.putStringPrefs
import com.attendit.booxapp.databinding.ActivityMainBinding
import com.attendit.booxapp.databinding.MainActivityBinding
import com.attendit.booxapp.model.BookModel
import com.attendit.booxapp.model.UserModel
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private val mAuthStateListener: AuthStateListener? = null

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        if (i.getStringExtra("type") != null) {
            if (intent.getStringExtra("type") == "signup") {
                val current_user = UserModel()
                current_user.name = intent.getStringExtra("name")
                current_user.email = intent.getStringExtra("email")
                current_user.loc = intent.getStringExtra("loc")
                current_user.phone = intent.getStringExtra("mob")
                putStringPrefs(applicationContext, "user_loc", intent.getStringExtra("loc"))
                sendToFirebase(current_user)
            } else if (intent.getStringExtra("type") == "signin") {
                userDetails
            }
        }


//        toolbar = (Toolbar) findViewById(R.id.mytoolbar);

        binding.menuIcon!!.setOnClickListener { }
        putStringPrefs(applicationContext, "current_userid", mFirebaseAuth.currentUser!!.uid)
        //        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FirebaseAuth.getInstance().signOut();
//                Intent signup = new Intent(MainActivity.this, SignIn.class);
//                startActivity(signup);
//            }
//        });

        //  setSupportActionBar(toolbar);
        setupViewPager(binding.myViewPager)
        binding.tablayout!!.setupWithViewPager(binding.myViewPager)
    }

    private val userDetails: Unit
        private get() {
            val id = FirebaseAuth.getInstance().currentUser!!.uid
            val ref = FirebaseDatabase.getInstance().getReference("User/$id")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    putStringPrefs(applicationContext, "user_loc", user!!.loc)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

    private fun sendToFirebase(current_user: UserModel) {
        val user_id = FirebaseAuth.getInstance().currentUser!!.uid
        current_user.id = user_id
        val user_ref = FirebaseDatabase.getInstance().getReference("Users/$user_id")
        user_ref.setValue(current_user)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        addFragment(RentFragment(), "Rent")
        addFragment(SellFragment(), "Sell")
        addFragment(PurchaseFragment(), "Purchase")
        viewPager!!.adapter = viewPagerAdapter
    }
}