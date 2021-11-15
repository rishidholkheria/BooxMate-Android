package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityEditProfileBinding
import com.booxapp.model.UserModel
import com.google.firebase.database.*

class MyProfile : AppCompatActivity() {

    lateinit var uDatabase: DatabaseReference
    lateinit var uid: String
    lateinit var tid: String
    var myDataListModel: ArrayList<UserModel> = ArrayList()


    lateinit var uname: String
    lateinit var uemail: String
    lateinit var uphoneNumber: String
    lateinit var uloc: String

    lateinit var binding: ActivityEditProfileBinding

    var TAG = "My Profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

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


        //--------PROBLEM---------
        binding.confirmEditChanges.setOnClickListener(View.OnClickListener {
            val editUsername = binding.editProfileUsername!!.getText().toString().trim { it <= ' ' }
            val editMno = binding.editProfileMobilenumber!!.getText().toString().trim { it <= ' ' }
            val editLoc = binding.editProfileLocation!!.getText().toString().trim { it <= ' ' }

        })

        binding.purchasedBooks.setOnClickListener(View.OnClickListener {
            val i = Intent(this, MyOrders::class.java)
            startActivity(i)
        })


    }

    private fun loadUserData() {
        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    var myDataListModelInternal = child.getValue(UserModel::class.java)
                    if (myDataListModelInternal != null && myDataListModelInternal.userId!! == uid) {
//                        uname = myDataListModelInternal.name.toString()
//                        uloc = myDataListModelInternal.loc.toString()
//                        uemail = myDataListModelInternal.email.toString()
//                        uphoneNumber = myDataListModelInternal.phone.toString()

//                        binding.editProfileUsername.setText(uname)
//                        binding.editProfileLocation.setText(uloc)
//                        binding.editProfileEmail.setText(uemail)
//                        binding.editProfileMobilenumber.setText(uphoneNumber)

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

//    private fun saveUserDetails(userModel: ArrayList<UserModel>, onCompleteListener: onCompleteFirebase) {
//        uDatabase.child(tid!!).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                uDatabase.child(tid!!).updateChildren(
//                    userModel,
//                    DatabaseReference.CompletionListener { error, ref ->
//                        if (error == null) {
//                            onCompleteListener.onCallback(true)
//
//                        } else {
//                            Log.e(TAG, "Remove of " + ref + " failed: " + error.message)
//                            onCompleteListener.onCallback(false)
//                        }
//                    })
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }

}
