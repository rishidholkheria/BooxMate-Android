package com.booxapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BookDetails : AppCompatActivity() {

    var bottomSheetDialog: BottomSheetDialog? = null
    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var mTextViewState: TextView? = null
    private val user = FirebaseAuth.getInstance().currentUser
    private var request_refrence: DatabaseReference? = null
    private var current_bookid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
//        val t = findViewById<TextView>(R.id.title1)
        val img = findViewById<ImageView>(R.id.imageView)
        val purchase_request = findViewById<View>(R.id.request) as Button
        val a = intent.getStringExtra("btitle")
        val b = intent.getStringExtra("image")
        val c = intent.getStringExtra("bctgry")
        current_bookid = intent.getStringExtra("book_id")
        Glide.with(this).load(b).into(img)

    }
}