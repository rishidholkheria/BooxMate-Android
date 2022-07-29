package com.booxapp

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.adapter.MyOrdersAdapter
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityEditProfileBinding
import com.booxapp.model.BookModel
import com.booxapp.model.UserModel
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class MyProfile : AppCompatActivity() {

    lateinit var uDatabase: DatabaseReference
    lateinit var bDatabase: DatabaseReference

    lateinit var uid: String
    lateinit var tid: String

    lateinit var binding: ActivityEditProfileBinding
    var adapter: MyOrdersAdapter? = null
    lateinit var bookId: String
    var purchasedBooks: ArrayList<String> = ArrayList()

    var TAG = "My Profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        binding.toolbarLayout.toolbar.overflowIcon?.setTint(Color.WHITE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //For back btn
        supportActionBar?.setDisplayShowHomeEnabled(true) //Both lines for back btn

        bDatabase = FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)
        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

        var myDataListModel: ArrayList<BookModel> = ArrayList()

        Toast.makeText(applicationContext, "My profile activity opened", Toast.LENGTH_SHORT).show()

        uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        )!!

        tid = Prefs.getStringPrefs(
            applicationContext,
            "Id"
        ).toString()

        loadUserData()

        binding.confirmEditChanges.setOnClickListener(View.OnClickListener {
            val editUsername = binding.editProfileUsername!!.getText().toString().trim { it <= ' ' }
            val editMno = binding.editProfileMobilenumber!!.getText().toString().trim { it <= ' ' }
            val editLoc = binding.editProfileLocation!!.getText().toString().trim { it <= ' ' }

            saveUserDetails(editUsername, editMno, editLoc)

        })

        binding.purchasedBooksRecycler.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        adapter = MyOrdersAdapter(applicationContext, myDataListModel)
        binding.purchasedBooksRecycler!!.adapter = adapter

        getPurchasedBooks(uid!!)

        bDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
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

    //for back btn toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadUserData() {
        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    var myDataListModelInternal = child.getValue(UserModel::class.java)
                    if (myDataListModelInternal != null && myDataListModelInternal.userId!! == uid) {

                        binding.editProfileUsername.setText(myDataListModelInternal.name)
                        binding.editProfileLocation.setText(myDataListModelInternal.loc)
                        binding.editProfileEmail.setText(myDataListModelInternal.email)
                        binding.editProfileMobilenumber.setText(myDataListModelInternal.phone)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun saveUserDetails(name: String, mobile: String, location: String) {

        uDatabase.child(tid!!).child("name").setValue(name)
        uDatabase.child(tid!!).child("phone").setValue(mobile)
        uDatabase.child(tid!!).child("loc").setValue(location)

        Toast.makeText(applicationContext, "Changes Saved", Toast.LENGTH_SHORT).show()
    }

    private fun getPurchasedBooks(uid: String) {
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

//Update children example code

//    fun attachWishListWithUser(onCompleteListener: onCompleteFirebase) {
//
//        val userLessonsValues = umodel.toMap()
//
//        val childUpdates = hashMapOf<String, Any>(
//            "/$tid" to userLessonsValues
//        )
//        uDatabase.updateChildren(childUpdates)
//
//        onCompleteListener.onCallback(true)
//    }

}
