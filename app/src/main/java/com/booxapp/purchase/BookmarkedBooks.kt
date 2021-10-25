package com.booxapp.purchase

import android.app.ProgressDialog
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.Constants
import com.booxapp.adapter.BookmarkAdapter
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityBookmarkedBooksBinding
import com.booxapp.model.BookModel
import com.booxapp.model.UserModel
import com.google.firebase.database.*

class BookmarkedBooks : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference
    lateinit var uDatabase: DatabaseReference

    var adapter: BookmarkAdapter? = null
    private var progressDialog: ProgressDialog? = null
    lateinit var binding: ActivityBookmarkedBooksBinding

    lateinit var bookId: String

    var bookmarkModel : ArrayList<UserModel> = ArrayList()

    private val TAG = "Bookmarked books"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookmarkedBooksBinding.inflate(layoutInflater)

        var myDataListModel: ArrayList<BookModel> = ArrayList()
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)
        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        adapter = BookmarkAdapter(applicationContext, myDataListModel)
        binding.recyclerView!!.adapter = adapter

        var uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        )

        getBookMarkedBooks(uid!!)


//        mDatabase.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                myDataListModel.clear()
//                for (child in snapshot.children) {
//                    child.key?.let { Log.i(TAG, it) }
//                    var myDataListModelInternal = child.getValue(BookModel::class.java)
//
//                    if (myDataListModelInternal != null && myDataListModelInternal.id != uid) {
//                        var title: String? = myDataListModelInternal.title
//                        var offered_price: String? = myDataListModelInternal.offeredprice
//                        var mrp: String? = myDataListModelInternal.mrp
//                        var id: String? = myDataListModelInternal.id
//                        var location: String? = myDataListModelInternal.location
//                        var category: String? = myDataListModelInternal.category
//                        var description: String? = myDataListModelInternal.description
//                        var bookimage: String? = myDataListModelInternal.imagelink
//                        var userid: String? = myDataListModelInternal.userId
//
//                        myDataListModel.add(
//                            BookModel(
//                                title,
//                                location,
//                                mrp,
//                                id,
//                                offered_price,
//                                category,
//                                true,
//                                description,
//                                bookimage,
//                                userid
//                            )
//                        )
//                    }
//                }
//                adapter!!.notifyDataSetChanged()
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        })


        progressDialog = ProgressDialog(applicationContext)
        progressDialog!!.setMessage("Please wait...")


    }

    private fun getBookMarkedBooks(uid: String) {
        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    if (child.child("userId").value.toString() == uid) {
                        var internalBookmarkModel =
                            child.child("bookmarkedBooks").value
                        Log.e(TAG, internalBookmarkModel.toString())
                        Log.e(TAG, uid)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}