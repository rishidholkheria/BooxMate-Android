package com.booxapp.purchase

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.MenuItem
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
    lateinit var bDatabase: DatabaseReference
    lateinit var uDatabase: DatabaseReference

    var adapter: BookmarkAdapter? = null
    lateinit var binding: ActivityBookmarkedBooksBinding

    lateinit var bookId: String

    var bookmarkedBooks: ArrayList<String> = ArrayList()

    private val TAG = "Bookmarked books"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookmarkedBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toobar)

        binding.toolbarLayout.toobar.overflowIcon?.setTint(Color.WHITE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //For back btn
        supportActionBar?.setDisplayShowHomeEnabled(true) //Both lines for back btn



        var myDataListModel: ArrayList<BookModel> = ArrayList()
        bDatabase = FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)
        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

        binding.bookmarkedBooksRecycler.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        adapter = BookmarkAdapter(applicationContext, myDataListModel)
        binding.bookmarkedBooksRecycler!!.adapter = adapter

        var uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        )

        getBookMarkedBooks(uid!!)

        bDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }
                    for(subChild in bookmarkedBooks){
                        var myDataListModelInternal = child.getValue(BookModel::class.java)
                        if (myDataListModelInternal != null && myDataListModelInternal.id!! == subChild) {
                            var title: String? = myDataListModelInternal.title
                            var offered_price: String? = myDataListModelInternal.offeredprice
                            var mrp: String? = myDataListModelInternal.mrp
                            var id: String? = myDataListModelInternal.id
                            var location: String? = myDataListModelInternal.location
                            var city: String? = myDataListModelInternal.city!!
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
                        else{
                            Log.i(TAG, "Testing it is!")
                        }
                    }
                }
                adapter!!.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })



    }

    private fun getBookMarkedBooks(uid: String) {
        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    if (child.child("userId").value.toString() == uid) {
                        bookmarkedBooks = child.child("bookmarkedBooks").value as ArrayList<String>
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