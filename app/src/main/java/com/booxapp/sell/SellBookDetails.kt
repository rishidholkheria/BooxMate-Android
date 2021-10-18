package com.booxapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.databinding.ActivityBookDetailsBinding
import com.booxapp.databinding.SellBookDetailsBinding
import com.booxapp.model.BookModel
import com.booxapp.model.UserModel
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SellBookDetails : AppCompatActivity() {

    lateinit var bookmarkModel: UserModel
//    lateinit var dialog: Dialog

    lateinit var binding: ActivityBookDetailsBinding
    lateinit var singleBookData: BookModel

    var ref: DatabaseReference? = null
    var bookmarkedBook: MutableList<String> = ArrayList()

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

        var bId = bundle!!.getString("bookid")
        Log.e("userrrrrrrr", bId!!)
        var title = bundle!!.getString("booktitle")
        Toast.makeText(applicationContext, bId, Toast.LENGTH_LONG).show()
        Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
//        UserModel(bId)

        bookmarkedBook.add(bId)

        binding.bookmark.setOnClickListener(View.OnClickListener {
            FirebaseAdapter(applicationContext).addBookmark( UserModel(bookmarkedBook),
                object : onCompleteFirebase {
                    override fun onCallback(value: Boolean) {
//                        dialog.dismiss()
                        Toast.makeText(applicationContext, "Done", Toast.LENGTH_LONG).show()
                    }
                })
        })

    }
}