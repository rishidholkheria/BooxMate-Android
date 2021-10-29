package com.booxapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityEditProfileBinding
import com.booxapp.model.ExchangeModel
import com.booxapp.model.UserModel
import com.google.firebase.database.*

class MyProfile : AppCompatActivity() {


    lateinit var uDatabase: DatabaseReference
    lateinit var uid: String
    lateinit var tid: String

    lateinit var binding: ActivityEditProfileBinding

    var TAG = "My Profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

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

//            uDatabase.child(tid!!)
//                .addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//                        uDatabase.child(tid!!).setValue(
//                                UserModel(editUsername, editMno, editLoc),
//                                    DatabaseReference.CompletionListener { error, ref ->
//                                    if (error == null) {
//                                        onCompleteListener.onCallback(true)
//
//                                    } else {
//                                        Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
//                                        onCompleteListener.onCallback(false)
//                                    }
//                                })
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//                })
//
//        })


        })


    }

    private fun loadUserData() {
        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    if (child.child("userId").value.toString() == uid) {
                        binding.editProfileUsername.hint =
                            child.child("name").value.toString()
                        binding.editProfileLocation.hint = child.child("location").value.toString()
                        binding.editProfileEmail.hint = child.child("email").value.toString()
                        binding.editProfileMobilenumber.hint = child.child("phone").value.toString()
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
