package com.attendit.booxapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SellBookDetails : AppCompatActivity() {
    var backbtn1: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sell_book_details)
        backbtn1 = findViewById<View>(R.id.backbtn) as ImageButton
        backbtn1!!.setOnClickListener {
            val i = Intent(this@SellBookDetails, MainActivity::class.java)
            startActivity(i)
        }
    }
}