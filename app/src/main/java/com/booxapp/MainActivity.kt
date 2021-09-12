package com.booxapp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.booxapp.adapter.ViewPagerAdapter
import com.booxapp.adapter.ViewPagerAdapter.Companion.addFragment
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
        val userid = FirebaseDatabase.getInstance().reference.key
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        current_user.id = id
        val user_ref = FirebaseDatabase.getInstance().getReference("Users/$userid")
        user_ref.setValue(current_user)
    }

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