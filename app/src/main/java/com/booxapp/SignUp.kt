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
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    var proceed: CardView? = null
    var btsignin: CardView? = null
    var name: EditText? = null
    var email: EditText? = null
    var mno: EditText? = null
    var password: EditText? = null
    var loc: EditText? = null
    var mFirebaseAuth: FirebaseAuth? = null

    var TAG = "SignUp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        proceed = findViewById(R.id.create_account)
        btsignin = findViewById(R.id.backtosignin)
        name = findViewById(R.id.username)
        email = findViewById(R.id.email)
        mno = findViewById(R.id.mobilenumber)
        password = findViewById(R.id.password)
        loc = findViewById(R.id.location)
        mFirebaseAuth = FirebaseAuth.getInstance()
        proceed!!.setOnClickListener(View.OnClickListener {
            val username = name!!.getText().toString().trim { it <= ' ' }
            val useremail = email!!.getText().toString().trim { it <= ' ' }
            val usermno = mno!!.getText().toString().trim { it <= ' ' }
            val userpass = password!!.getText().toString().trim { it <= ' ' }
            val location = loc!!.getText().toString().trim { it <= ' ' }
            if (username.isEmpty()) {
                name!!.setError("Name Required!")
                name!!.requestFocus()
            } else if (useremail.isEmpty()) {
                email!!.setError("EmailID required!")
                email!!.requestFocus()
            } else if (usermno.isEmpty()) {
                mno!!.setError("Mobile Number required!")
                mno!!.requestFocus()
            } else if (location.isEmpty()) {
                loc!!.setError("Enter your Location!")
                loc!!.requestFocus()
            } else if (userpass.isEmpty()) {
                password!!.setError("Enter a Password!")
                password!!.requestFocus()
            } else if (!(username.isEmpty() && useremail.isEmpty() && usermno.isEmpty() && userpass.isEmpty())) {
                mFirebaseAuth!!.createUserWithEmailAndPassword(useremail, userpass).addOnCompleteListener(this@SignUp) { task ->
                    if (!task.isSuccessful) {
                        try {
                            throw task.exception!!
                        } catch (e: Exception) {
                            Log.i(TAG, e.message!!)
                        }
                        Toast.makeText(this@SignUp, "Something wrong in Details!", Toast.LENGTH_LONG).show()
                    } else {
                        val i = Intent(this@SignUp, MainActivity::class.java)
                        i.putExtra("name", username)
                        i.putExtra("email", useremail)
                        i.putExtra("loc", location)
                        i.putExtra("mob", usermno)
                        i.putExtra("type", "signup")
                        startActivity(i)
                        Toast.makeText(this@SignUp, "Done", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this@SignUp, "Enter All Details", Toast.LENGTH_LONG).show()
            }
        })
        btsignin!!.setOnClickListener(View.OnClickListener {
            val i = Intent(this@SignUp, SignIn::class.java)
            startActivity(i)
        })
    }
}