package com.booxapp

import android.content.Context
import android.util.Log
import com.booxapp.data.Prefs
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel
import com.booxapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import kotlin.math.log


class FirebaseAdapter(var context: Context) {

    private val TAG = "FirebaseAdapter"

    var mDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)

    var eDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.EX_DB_NAME)

    var uDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var uid = Prefs.getStringPrefs(
        context,
        "userId"
    )
    var myKey = "";

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
        var id: String? = uDatabase.child("users").push().key

//        uDatabase.child("users").child("id").equalTo(uid).addListenerForSingleValueEvent(object: ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                dataSnapshot.children.forEach {
//                    key= it.key.toString()
//                    Log.i(TAG,"keyyyyyyyyyy: "+key)
//                }
//            }
//            override fun onCancelled(p0: DatabaseError) {
//                //do whatever you need
//            }
//        })

//logging all ids
        val database_listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    myKey = ds.key.toString()
                    if (uDatabase.child("users").child(myKey!!).child("id").equals(uid)){
                        uDatabase.child("users").child(myKey!!).child("bookmarkedBooks")
                                .setValue(userModel, DatabaseReference.CompletionListener { error, ref ->
                                    if (error == null) {
                                        onCompleteListener.onCallback(true)
                                    } else {
                                        Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
                                        onCompleteListener.onCallback(false)
                                    }
                                })
                    }
                    Log.e(TAG, "idddddddddddddddd" + myKey)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        uDatabase.addValueEventListener(database_listener)




//            uDatabase.child("users").child(myKey!!).child("bookmarkedBooks")
//                    .setValue(userModel, DatabaseReference.CompletionListener { error, ref ->
//                        if (error == null) {
//                            onCompleteListener.onCallback(true)
//                        } else {
//                            Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
//                            onCompleteListener.onCallback(false)
//                        }
//                    })
        }
    }