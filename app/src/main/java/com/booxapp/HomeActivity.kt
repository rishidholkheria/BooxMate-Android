package com.booxapp

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.BookBuzz.BookBuzz
import com.booxapp.adapter.HomeAdapter
import com.booxapp.adapter.MyAdapter
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityHomeBinding
import com.booxapp.databinding.FragmentPurchaseBinding
import com.booxapp.databinding.MainActivityBinding
import com.booxapp.model.BookModel
import com.booxapp.purchase.BookmarkedBooks
import com.booxapp.store.BooxStore
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var mDatabase: DatabaseReference
    var homeAdapter: HomeAdapter? = null
    private var progressDialog: ProgressDialog? = null
    lateinit var category: String
    var myDataListModel: ArrayList<BookModel> = ArrayList()

    private val TAG = "HomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDatabase = FirebaseDatabase.getInstance().getReference("books")

        binding.popularRv.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        homeAdapter = HomeAdapter(applicationContext, myDataListModel)
        binding.popularRv!!.adapter = homeAdapter

        loadData()

        //menu items
        binding.store.setOnClickListener {
            val fragment = BooxStore()
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment).commit()
        }

        binding.secondhand.setOnClickListener {
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.myProfile.setOnClickListener {
            val intent = Intent(this@HomeActivity, MyProfile::class.java)
            startActivity(intent)
        }

        binding.bookbuzz.setOnClickListener {
            val intent = Intent(this@HomeActivity, BookBuzz::class.java)
            startActivity(intent)
        }
    }

    private fun loadData() {
        var uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        )

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }
                    var myDataListModelInternal = child.getValue(BookModel::class.java)
                    if (myDataListModelInternal != null && myDataListModelInternal.userId != uid) {
                        var title: String? = myDataListModelInternal.title
                        var offered_price: String? = myDataListModelInternal.offeredprice
                        var mrp: String? = myDataListModelInternal.mrp
                        var id: String? = myDataListModelInternal.id
                        var location: String? = myDataListModelInternal.location
                        var city: String? = myDataListModelInternal.city
                        var category: String? = myDataListModelInternal.category
                        var description: String? = myDataListModelInternal.description
                        var bookimage: String? = myDataListModelInternal.imagelink
                        var userid: String? = myDataListModelInternal.userId

                        myDataListModel.add(
                            BookModel(
                                title,
                                location,
                                city,
                                mrp,
                                id,
                                offered_price,
                                category,
                                description,
                                bookimage,
                                userid,
                                false
                            )
                        )
                    }
                }
                homeAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })


        progressDialog = ProgressDialog(applicationContext)
        progressDialog!!.setMessage("Please wait...")
    }
}