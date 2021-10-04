package com.booxapp

import android.content.Context
import android.util.Log
import com.booxapp.data.Prefs
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseAdapter(var context: Context) {

    private val TAG = "FirebaseAdapter"

    var mDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)

    var eDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.EX_DB_NAME)

    var uid = Prefs.getStringPrefs(
        context,
        "userId"
    )

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

}