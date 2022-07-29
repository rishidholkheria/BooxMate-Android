package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.adapter.ExBookMarkAdapter
import com.booxapp.adapter.SellAdapter
import com.booxapp.data.Prefs
import com.booxapp.databinding.FragmentExBookmarkBinding
import com.booxapp.databinding.FragmentExPostBinding
import com.booxapp.databinding.FragmentExchangeBinding
import com.booxapp.databinding.FragmentSellBinding
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*

class ExBookMarkFragment : Fragment() {

    lateinit var binding: FragmentExBookmarkBinding

    lateinit var mDatabase: DatabaseReference
    lateinit var uDatabase: DatabaseReference
    lateinit var uid: String

    var eadapter: ExBookMarkAdapter? = null

    var bookmarkedBooks: ArrayList<String> = ArrayList()


    private val TAG = "ExBookmarkFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExBookmarkBinding.inflate(inflater, container, false)

        uid = Prefs.getStringPrefs(
            requireContext(),
            "userId"
        )!!

        var myDataListModel: ArrayList<ExchangeModel> = ArrayList()
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.EX_DB_NAME)
        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

        binding.sellrecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        eadapter = ExBookMarkAdapter(requireContext(), myDataListModel)
        binding.sellrecycler!!.adapter = eadapter

        getBookMarkedBooks(uid!!)

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }

                    for (subChild in bookmarkedBooks) {
                        var myDataListModelInternal = child.getValue(ExchangeModel::class.java)
                        if (myDataListModelInternal != null && myDataListModelInternal.id!! == subChild) {
                            var title: String? = myDataListModelInternal.title
                            var location: String? = myDataListModelInternal.location
                            var city: String? = myDataListModelInternal.city
                            var category: String? = myDataListModelInternal.category
                            var mrp: String? = myDataListModelInternal.mrp
                            var description: String? = myDataListModelInternal.description
                            var bookimage: String? = myDataListModelInternal.imagelink
                            var id: String? = myDataListModelInternal.id
                            var userId: String? = myDataListModelInternal.userId
                            var expectedBooks: String? = myDataListModelInternal.expectedBooks


                            myDataListModel.add(
                                ExchangeModel(
                                    title,
                                    location,
                                    city,
                                    category,
                                    mrp!!,
                                    expectedBooks,
                                    description,
                                    id,
                                    bookimage,
                                    userId,
                                    false
                                )
                            )
                        }
                    }
                }
                eadapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        return binding.root
    }

    private fun getBookMarkedBooks(uid: String) {
        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    if (child.child("userId").value.toString() == uid) {
                        bookmarkedBooks =
                            child.child("exchangeBookmarkedBooks").value as ArrayList<String>
                    }
                }
                Log.e(TAG, bookmarkedBooks.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}