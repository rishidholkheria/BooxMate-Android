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
import kotlin.math.log


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

    fun addBookmark(userModel: UserModel, onCompleteListener: onCompleteFirebase) {

        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    Log.i("Pref Id", "" + tid)
                    Log.i("Child Id", "" + ds.key.toString())
                    if (ds.key.toString().equals("Mm9TWKDM8zjmQtDvyt_")) {
                        uDatabase.child(tid!!).child("bookmarkedBooks")
                            .setValue(
                                userModel,
                                DatabaseReference.CompletionListener { error, ref ->
                                    if (error == null) {
                                        onCompleteListener.onCallback(true)

                                    } else {
                                        Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
                                        onCompleteListener.onCallback(false)
                                    }
                                })
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


//logging all ids
//        uDatabase.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (ds in dataSnapshot.children) {
//                    myKey = ds.key.toString()
//                    Log.e(TAG, "id" + myKey)
//                    Log.e(TAG, "uid" + uid)
//                    /*if (uDatabase.child("users").child(myKey!!).child("id").equals(uid)) {
//                        uDatabase.child("users").child(myKey!!).child("bookmarkedBooks")
//                            .setValue(
//                                userModel,
//                                DatabaseReference.CompletionListener { error, ref ->
//                                    if (error == null) {
//                                        onCompleteListener.onCallback(true)
//                                    } else {
//                                        Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
//                                        onCompleteListener.onCallback(false)
//                                    }
//                                })
//                    }*/
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })

    }
}