package com.attendit.booxapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.attendit.booxapp.adapter.PurchaseCartAdapter
import com.attendit.booxapp.model.BookModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class PurchaseCart : AppCompatActivity() {
    var cartrecyclerView: RecyclerView? = null
    private var cartLayoutManager: LinearLayoutManager? = null
    private val cartdata = ArrayList<BookModel>()
    private val user = FirebaseAuth.getInstance().currentUser
    private var bookmarkedBooks: ArrayList<String?>? = null
    private var purchaseCartAdapter: PurchaseCartAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_cart)

        cartrecyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
        cartLayoutManager = LinearLayoutManager(this.applicationContext)


        Log.d("debugMode", "The application stopped after this")
        cartrecyclerView!!.layoutManager = cartLayoutManager
        purchaseCartAdapter = PurchaseCartAdapter(applicationContext, cartdata)
        cartrecyclerView!!.adapter = purchaseCartAdapter
        // fill_data();
    }

    private val bookmarked: Unit
        private get() {
            bookmarkedBooks = ArrayList()
            val reference = FirebaseDatabase.getInstance().reference.child("Bookmarked").child(user!!.uid)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    cartdata.clear()
                    bookmarkedBooks!!.clear()
                    for (dataSnapshot in snapshot.children) {
                        bookmarkedBooks!!.add(dataSnapshot.key)
                    }
                    readBookmarks()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

    private fun readBookmarks() {
        val reference = FirebaseDatabase.getInstance().reference.child("books")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartdata.clear()
                for (datasnapshot in snapshot.children) {
                    val bookmark = datasnapshot.getValue(BookModel::class.java)
                    for (id in bookmarkedBooks!!) {
                        if (bookmark!!.id == id) {
                            cartdata.add(bookmark)
                        }
                    }
                }
                purchaseCartAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}