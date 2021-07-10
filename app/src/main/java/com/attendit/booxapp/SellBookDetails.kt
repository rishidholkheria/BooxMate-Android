package com.attendit.booxapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.attendit.booxapp.databinding.SellBookDetailsBinding

class SellBookDetails : AppCompatActivity() {

    lateinit var binding: SellBookDetailsBinding

    var backbtn1: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SellBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backbtn1 = findViewById<View>(R.id.backbtn) as ImageButton
        backbtn1!!.setOnClickListener {
            val i = Intent(this@SellBookDetails, MainActivity::class.java)
            startActivity(i)
        }

        val bundle = intent.extras
        var book_title: String? = bundle!!.getString("booktitle", "Book Title")
        var offered_price: String? = bundle!!.getString("oprice", "Offered Price")

        binding.title1.text = book_title
        binding.tvPrice.text = offered_price
//        Toast.makeText(applicationContext, book_title, Toast.LENGTH_SHORT).show()
    }
}