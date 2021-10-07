package com.booxapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ForgetPassword : AppCompatActivity() {

    var mFirebaseAuth: FirebaseAuth? = null
    lateinit var binding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFirebaseAuth = FirebaseAuth.getInstance()

        binding.sendLinkBtn!!.setOnClickListener(View.OnClickListener {
            val fEmailid = binding.forgetPassEmail!!.getText().toString()

            if (fEmailid.isEmpty()) {
                binding.forgetPassEmail!!.setError("Email Required!")
                binding.forgetPassEmail!!.requestFocus()
            }

            mFirebaseAuth!!.sendPasswordResetEmail(fEmailid)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Link sent to your Email.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Some error occurred, Try Again!", Toast.LENGTH_LONG).show()
                    }
                }
        })

    }
}