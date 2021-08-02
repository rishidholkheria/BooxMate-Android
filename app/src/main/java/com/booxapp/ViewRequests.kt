package com.booxapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.adapter.RequestAdapter
import com.booxapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class ViewRequests : AppCompatActivity() {
    private var requestrecycler: RecyclerView? = null
    private var requestAdapter: RequestAdapter? = null
    private var reqLayoutmanager: LinearLayoutManager? = null
    private val req_data = ArrayList<UserModel?>()
    private val user = FirebaseAuth.getInstance().currentUser
    private var req_ref: DatabaseReference? = null
    private var book_id: String? = null
    private var users: ArrayList<String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_cart)
        book_id = intent.getStringExtra("book_id")
        requests
        requestrecycler = findViewById<View>(R.id.request_recycler) as RecyclerView
        reqLayoutmanager = LinearLayoutManager(this.applicationContext)
        Log.d("debugMode", "The application stopped after this")
        requestrecycler!!.layoutManager = reqLayoutmanager
        requestAdapter = RequestAdapter(applicationContext, req_data)
        requestrecycler!!.adapter = requestAdapter
        // fill_data();
    }

    private val requests: Unit
        private get() {
            users = ArrayList()
            req_ref = FirebaseDatabase.getInstance().getReference("PurchaseRequest/$book_id")
            req_ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    req_data.clear()
                    users!!.clear()
                    for (dataSnapshot in snapshot.children) {
                        users!!.add(dataSnapshot.key)
                    }
                    reqData
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    private val reqData: Unit
        private get() {
            val reference = FirebaseDatabase.getInstance().reference.child("Users")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    req_data.clear()
                    for (datasnapshot in snapshot.children) {
                        val users1 = datasnapshot.getValue(UserModel::class.java)
                        for (id in users!!) {
                            if (users1!!.id == id) {
                                req_data.add(users1)
                            }
                        }
                    }
                    requestAdapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
}