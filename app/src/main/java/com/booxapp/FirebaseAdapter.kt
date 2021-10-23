package com.booxapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.booxapp.data.Prefs
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel
import com.booxapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot


class FirebaseAdapter {

    private val TAG = "FirebaseAdapter"

    private var context: Context

    var mDatabase: DatabaseReference

    var eDatabase: DatabaseReference

    var uDatabase: DatabaseReference

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var uid = ""
    var tid = ""


    constructor(context: Context) {

        this.context = context
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)
        eDatabase = FirebaseDatabase.getInstance().getReference(Constants.EX_DB_NAME)
        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

        uid = Prefs.getStringPrefs(
            context,
            "userId"
        ).toString()

        tid = Prefs.getStringPrefs(
            context,
            "Id"
        ).toString()

    }

    fun addNewBook(bookModel: BookModel, onCompleteListener: onCompleteFirebase) {
        var id: String? = mDatabase.child("books").push().key
        bookModel.id = id
        bookModel.userId = uid
        mDatabase.child(id!!)
            .setValue(bookModel, DatabaseReference.CompletionListener { error, ref ->
                if (error == null) {
                    onCompleteListener.onCallback(true)
                } else {
                    Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
                    onCompleteListener.onCallback(false)
                }
            })
    }


    fun addNewExBook(bookModel: ExchangeModel, onCompleteListener: onCompleteFirebase) {
        var id: String? = eDatabase.child("exchangeBooks").push().key
        bookModel.userId = uid
        bookModel.id = id
        eDatabase.child(id!!)
            .setValue(bookModel, DatabaseReference.CompletionListener { error, ref ->
                if (error == null) {
                    onCompleteListener.onCallback(true)
                } else {
                    Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
                    onCompleteListener.onCallback(false)
                }
            })
    }

    fun addBookmark(bookId: String, onCompleteListener: onCompleteFirebase) {

        uDatabase.child(tid!!).child("bookmarkedBooks")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    uDatabase.child(tid!!).child("bookmarkedBooks")
                        .child("${dataSnapshot.childrenCount}").setValue(
                            bookId,
                            DatabaseReference.CompletionListener { error, ref ->
                                if (error == null) {
                                    onCompleteListener.onCallback(true)

                                } else {
                                    Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
                                    onCompleteListener.onCallback(false)
                                }
                            })
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun requestSeller(sellerId: String, bookName: String) {
        uDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
//                    sellerId = child.child("userId").value.toString()
                    if (child.child("userId").value.toString() == sellerId) saveBuyerId(
                        child.key.toString(),
                        bookName,
                        object : onCompleteFirebase {
                            override fun onCallback(value: Boolean) {
                                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                            }
                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun saveBuyerId(sellerKey: String, bookName: String, onCompleteListener: onCompleteFirebase) {
        uDatabase?.child(sellerKey)?.child("requests")
            ?.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        uDatabase!!.child(sellerKey!!).child("requests")
                            .child("${dataSnapshot.childrenCount}").setValue(
                                UserModel(uid, bookName),
                                DatabaseReference.CompletionListener { error, ref ->
                                    if (error == null) {
                                        onCompleteListener.onCallback(true)

                                    } else {
                                        Log.e(
                                            TAG,
                                            "Remove of " + ref + " failed: " + error.message
                                        )
                                        onCompleteListener.onCallback(false)
                                    }
                                })

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
    }
}


