package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.*


class SignIn : AppCompatActivity() {

    var mFirebaseAuth: FirebaseAuth? = null
    private val mAuthStateListener: AuthStateListener? = null

    var mDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

    var myKey: String? = null

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFirebaseAuth = FirebaseAuth.getInstance()

        binding.signinBtn!!.setOnClickListener(View.OnClickListener {
            val siemailid = binding.emailsignin!!.getText().toString()
            val sipass = binding.passwordsignin!!.getText().toString()

            if (siemailid.isEmpty()) {
                binding.emailsignin!!.setError("Name Required!")
                binding.emailsignin!!.requestFocus()
            } else if (sipass.isEmpty()) {
                binding.passwordsignin!!.setError("EmailID required!")
                binding.passwordsignin!!.requestFocus()
            } else if (siemailid.isEmpty() && sipass.isEmpty()) {
                Toast.makeText(this@SignIn, "Enter Details", Toast.LENGTH_LONG).show()
            } else if (!(siemailid.isEmpty() && sipass.isEmpty())) {
                mFirebaseAuth!!.signInWithEmailAndPassword(siemailid, sipass)
                    .addOnCompleteListener(this@SignIn) { task ->
                        if (task.isSuccessful) {
                            storeAuthId()
                            val i = Intent(this@SignIn, MainActivity::class.java)
                            startActivity(i)
                            finish()
                            Toast.makeText(this@SignIn, "Logged In...", Toast.LENGTH_LONG).show()

                        } else {
                            Toast.makeText(
                                this@SignIn,
                                "Wrong Email or Password.",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.e("SignIn Error", task.toString())
                        }
                    }
            }
        })

        binding.signupPageBtn!!.setOnClickListener(View.OnClickListener {
            val i = Intent(this@SignIn, SignUp::class.java)
            startActivity(i)
        })

        binding.forgetPasswordPage!!.setOnClickListener(View.OnClickListener {
            val i = Intent(this@SignIn, ForgetPassword::class.java)
            startActivity(i)
        })

    }

    private fun storeAuthId() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Toast.makeText(this@SignIn, "User Not Found!", Toast.LENGTH_LONG).show()
            return;
        }
        val userId = user!!.uid

        Prefs.putStringPrefs(
            applicationContext,
            "userId",
            userId
        )

        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    if (child.child("userId").value.toString() == userId) {
                        myKey = child.key
                        break;
                    }
                }

                Log.e("User Id from Auth", userId!!)
                Log.e("User Id from Realtime", myKey!!)
                Prefs.putStringPrefs(
                    applicationContext,
                    "Id",
                    myKey
                )
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        /*mDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach {
                        myKey = it.key
                        Log.e("keyyyyyyyyy", myKey!!)
                        Prefs.putStringPrefs(
                            applicationContext,
                            "Id",
                            myKey
                        )
                    }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/

    }

}