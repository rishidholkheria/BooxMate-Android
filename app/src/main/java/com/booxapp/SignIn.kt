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
import com.booxapp.databinding.ActivitySignInBinding
import com.booxapp.databinding.MainActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class SignIn : AppCompatActivity() {

    var mFirebaseAuth: FirebaseAuth? = null
    private val mAuthStateListener: AuthStateListener? = null

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                            val i = Intent(this@SignIn, MainActivity::class.java)
                            i.putExtra("type", "signin")
                            startActivity(i)
                            finish()
                            Toast.makeText(this@SignIn, "Done", Toast.LENGTH_LONG).show()
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
    }

}