package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.booxapp.SignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class SignIn : AppCompatActivity() {
    var proceed: CardView? = null
    var signup: CardView? = null
    var siemail: EditText? = null
    var sipassword: EditText? = null
    var mFirebaseAuth: FirebaseAuth? = null
    private val mAuthStateListener: AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        proceed = findViewById(R.id.signin)
        signup = findViewById(R.id.register)
        siemail = findViewById(R.id.emailsignin)
        sipassword = findViewById(R.id.passwordsignin)
        mFirebaseAuth = FirebaseAuth.getInstance()

//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
//
//                if (mFirebaseUser != null){
//                    Toast.makeText(SignIn.this,"Logged into Boox",Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(SignIn.this, MainActivity.class);
//                    startActivity(i);
//                }
//                else {
//                    Toast.makeText(SignIn.this,"Please Login",Toast.LENGTH_LONG).show();
//
//                }
//            }
//        };
        proceed!!.setOnClickListener(View.OnClickListener {
            val siemailid = siemail!!.getText().toString()
            val sipass = sipassword!!.getText().toString()
            if (siemailid.isEmpty()) {
                siemail!!.setError("Name Required!")
                siemail!!.requestFocus()
            } else if (sipass.isEmpty()) {
                sipassword!!.setError("EmailID required!")
                sipassword!!.requestFocus()
            } else if (siemailid.isEmpty() && sipass.isEmpty()) {
                Toast.makeText(this@SignIn, "Enter Details", Toast.LENGTH_LONG).show()
            } else if (!(siemailid.isEmpty() && sipass.isEmpty())) {
                mFirebaseAuth!!.signInWithEmailAndPassword(siemailid, sipass).addOnCompleteListener(this@SignIn) { task ->
                    if (task.isSuccessful) {
                        val i = Intent(this@SignIn, MainActivity::class.java)
                        i.putExtra("type", "signin")
                        startActivity(i)
                        Toast.makeText(this@SignIn, "Done", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@SignIn, "Wrong Email or Password.", Toast.LENGTH_LONG).show()
                        Log.e("SignIn Error", task.toString())
                    }
                }
            }
        })
        signup!!.setOnClickListener(View.OnClickListener {
            val i = Intent(this@SignIn, SignUp::class.java)
            startActivity(i)
        })
    } //    @Override
    //    protected void onStart() {
    //        super.onStart();
    //        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    //    }
}