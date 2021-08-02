package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    var mAuth = FirebaseAuth.getInstance()
    var currentUser = mAuth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (currentUser == null) {
            not_initiated()
        } else {
            initiated()
        }
    }

    private fun initiated() {
        Handler().postDelayed({
            val splashIntent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(splashIntent)
            finish()
        }, 1000)
    }

    private fun not_initiated() {
        Handler().postDelayed({
            val splashIntent = Intent(this@SplashScreen, SignIn::class.java)
            startActivity(splashIntent)
            finish()
        }, 1000)
    }

    companion object {
        private const val SPLASH_SCREEN_TIME_OUT = 1000
    }
}