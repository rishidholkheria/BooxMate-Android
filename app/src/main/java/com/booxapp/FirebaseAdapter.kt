package com.booxapp

import android.content.Context
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseAdapter(var context: Context) {

    private val TAG = "FirebaseAdapter"

    var mDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)

    fun addNewImage(imageLink: String, onCompleteListener: onMaujKardiListener) {
        var id: String? = mDatabase.child("books").push().key;
        mDatabase.child(id!!).child("imagelink")
            .setValue(imageLink, DatabaseReference.CompletionListener { error, ref ->
                if (error == null) {
                    onCompleteListener.onCallback(true)
                } else {
                    Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
                    onCompleteListener.onCallback(false)
                }
            })
//        }
    }
}