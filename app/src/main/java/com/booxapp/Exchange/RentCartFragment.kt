package com.booxapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.adapter.RentCartAdapter
import com.booxapp.model.RentBookModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class RentCartFragment : Fragment() {
    var rentcartdata = ArrayList<RentBookModel?>()
    var rcLayoutManager: LinearLayoutManager? = null
    var rcrecycler: RecyclerView? = null
    var rentcartadapter: RentCartAdapter? = null
    private val user = FirebaseAuth.getInstance().currentUser
    private var bookmarkedBooks: ArrayList<String?>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rent_cart, container, false)
        bookmarked
        rcrecycler = view.findViewById<View>(R.id.rentcartrecycler) as RecyclerView
        rcLayoutManager = LinearLayoutManager(this.activity)
        Log.d("debugMode", "The application stopped after this")
        rcrecycler!!.layoutManager = rcLayoutManager
        rentcartadapter = RentCartAdapter(context, rentcartdata)
        rcrecycler!!.adapter = rentcartadapter
        return view
    }

    private val bookmarked: Unit
        private get() {
            bookmarkedBooks = ArrayList()
            val reference = FirebaseDatabase.getInstance().reference.child("RentBookmarked").child(user!!.uid)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    rentcartdata.clear()
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
        val reference = FirebaseDatabase.getInstance().reference.child("rentbooks")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                rentcartdata.clear()
                for (datasnapshot in snapshot.children) {
                    val bookmark = datasnapshot.getValue(RentBookModel::class.java)
                    for (id in bookmarkedBooks!!) {
                        if (bookmark!!.id == id) {
                            rentcartdata.add(bookmark)
                        }
                    }
                }
                rentcartadapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}