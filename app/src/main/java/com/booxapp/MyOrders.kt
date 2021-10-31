package com.booxapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.adapter.MyOrdersAdapter
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityBookmarkedBooksBinding
import com.booxapp.model.BookModel
import com.google.firebase.database.*

class MyOrders : AppCompatActivity() {
    lateinit var bDatabase: DatabaseReference
    lateinit var uDatabase: DatabaseReference

    var adapter: MyOrdersAdapter? = null
    lateinit var binding: ActivityBookmarkedBooksBinding

    lateinit var bookId: String

    var purchasedBooks: ArrayList<String> = ArrayList()

    private val TAG = "MyOrders"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookmarkedBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var myDataListModel: ArrayList<BookModel> = ArrayList()
        bDatabase = FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)
        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

        binding.bookmarkedBooksRecycler.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        adapter = MyOrdersAdapter(applicationContext, myDataListModel)
        binding.bookmarkedBooksRecycler!!.adapter = adapter

        var uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        )

        getPuchasedBooks(uid!!)

        bDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }
                    for(subChild in purchasedBooks){
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

    private fun getPuchasedBooks(uid: String) {
        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    if (child.child("userId").value.toString() == uid) {
                        purchasedBooks = child.child("purchasedBooks").value as ArrayList<String>
                    }
                }
                Log.e(TAG, purchasedBooks.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}