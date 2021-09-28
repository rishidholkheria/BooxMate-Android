package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.booxapp.SignUp
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivitySignUpBinding
import com.booxapp.databinding.MainActivityBinding
import com.booxapp.databinding.PastOrderBinding
import com.booxapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference


class SignUp : AppCompatActivity() {

    var mFirebaseAuth: FirebaseAuth? = null
    var ref: DatabaseReference? = null

    lateinit var binding: ActivitySignUpBinding

    var TAG = "SignUp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFirebaseAuth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().reference

        binding.createAccount!!.setOnClickListener(View.OnClickListener {
            val username = binding.username!!.getText().toString().trim { it <= ' ' }
            val useremail = binding.email!!.getText().toString().trim { it <= ' ' }
            val usermno = binding.mobilenumber!!.getText().toString().trim { it <= ' ' }
            val userpass = binding.password!!.getText().toString().trim { it <= ' ' }
            val location = binding.location!!.getText().toString().trim { it <= ' ' }

            if (username.isEmpty()) {
                binding.username!!.setError("Name Required!")
                binding.username!!.requestFocus()
            } else if (useremail.isEmpty()) {
                binding.email!!.setError("EmailID required!")
                binding.email!!.requestFocus()
            } else if (usermno.isEmpty()) {
                binding.mobilenumber!!.setError("Mobile Number required!")
                binding.mobilenumber!!.requestFocus()
            } else if (location.isEmpty()) {
                binding.location!!.setError("Enter your Location!")
                binding.location!!.requestFocus()
            } else if (userpass.isEmpty()) {
                binding.password!!.setError("Enter a Password!")
                binding.password!!.requestFocus()
            } else if (!(username.isEmpty() && useremail.isEmpty() && usermno.isEmpty() && userpass.isEmpty())) {

                //Check if user already exists already

                mFirebaseAuth!!.createUserWithEmailAndPassword(useremail, userpass)
                    .addOnCompleteListener(this@SignUp) { task ->
                        if (!task.isSuccessful) {
                            try {
                                throw task.exception!!
                            } catch (e: Exception) {
                                Log.i(TAG, e.message!!)
                            }
                            Toast.makeText(
                                this@SignUp,
                                "Something wrong in Details!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
//                            Prefs.putStringPrefs(
//                                applicationContext,
//                                "user_loc",
//                                intent.getStringExtra("loc")
//                            )

                            val current_user = UserModel()
                            current_user.name = username
                            current_user.email = useremail
                            current_user.loc = location
                            current_user.phone = usermno
                            current_user.password = userpass

                            sendToFirebase(current_user)

                            val i = Intent(this@SignUp, MainActivity::class.java)
                            startActivity(i)
                            Toast.makeText(this@SignUp, "Done", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this@SignUp, "Enter All Details", Toast.LENGTH_LONG).show()
            }
        })

//        binding.!!.setOnClickListener(View.OnClickListener {
//            val i = Intent(this@SignUp, SignIn::class.java)
//            startActivity(i)
//        })
    }

    private fun sendToFirebase(current_user: UserModel) {
        val userid =
            FirebaseDatabase.getInstance().getReference("users")
                .push().key.toString() //Firebase Id
        val id = FirebaseAuth.getInstance().currentUser!!.uid   //Firebase-Auth-id
        current_user.id = id
        val user_ref = FirebaseDatabase.getInstance().getReference("users").child(userid)
        user_ref.setValue(current_user)
    }

}