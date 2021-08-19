package com.booxapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.databinding.SellBookDetailsBinding

class SellBookDetails : AppCompatActivity() {

    lateinit var binding: SellBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SellBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        var book_title: String? = bundle!!.getString("booktitle", "Book Title")
        var offered_price: String? = bundle!!.getString("oprice", "Offered Price")
        var mrp: String? = bundle!!.getString("mrp", "mrp")
        var loc: String? = bundle!!.getString("location", "Offered Price")
        var category: String? = bundle!!.getString("ctgry", "Book Title")
        var description: String? = bundle!!.getString("desc", "Description...")
        var sellername: String? = bundle!!.getString("sellername", "Book Title")
        var selleremail: String? = bundle!!.getString("selleremail", "Offered Price")
        var book_image: String? = bundle!!.getString("image", "Offered Price")


        binding.bookName.text = book_title
        binding.bookOfferedPrice.text = offered_price
        binding.bookCtgry.text = category
        binding.bookLoc.text = loc
        binding.bookMrp.text = mrp
        binding.bookDesc.text = description
        binding.bookSellerName.text = sellername
        binding.bookSellerEmail.text = selleremail
//        Toast.makeText(applicationContext, book_title, Toast.LENGTH_SHORT).show()
    }
}