package com.booxapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.databinding.ActivityBookDetailsBinding
import com.booxapp.databinding.SellBookDetailsBinding
import com.booxapp.model.BookModel
import com.bumptech.glide.Glide

class SellBookDetails : AppCompatActivity() {

    lateinit var binding: ActivityBookDetailsBinding
    lateinit var singleBookData: BookModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        binding.bookName.text = bundle!!.getString("booktitle", "Book Title")
        binding.bookOfferedPrice.text = bundle!!.getString("oprice", "Rs ")
        binding.bookCtgry.text = bundle!!.getString("ctgry", "Category")
        binding.bookLoc.text = bundle!!.getString("location", "Book Title")
        binding.bookMrp.text = bundle!!.getString("mrp", "Rs ")
        binding.bookOp.text = bundle!!.getString("oprice", "Rs ")
        binding.bookDesc.text = bundle!!.getString("booktitle", "No description available.")
        Glide.with(this)
            .load(bundle!!.getString("image", "No Image"))
            .into(binding.bookImage);

//        binding.bookOfferedPrice.text = singleBookData.offeredprice
//        binding.bookCtgry.text = singleBookData.category
//        binding.bookLoc.text = singleBookData.location
//        binding.bookMrp.text = singleBookData.mrp
//        binding.bookOp.text = singleBookData.offeredprice
//        binding.bookDesc.text = singleBookData.description

//        binding.bookSellerName.text = sellername
//        binding.bookSellerEmail.text = selleremail
//        Toast.makeText(applicationContext, book_title, Toast.LENGTH_SHORT).show()
    }
}