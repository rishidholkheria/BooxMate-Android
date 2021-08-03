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
        val t = findViewById<TextView>(R.id.title1)
        val img = findViewById<ImageView>(R.id.imageView)
        val purchase_request = findViewById<View>(R.id.request) as Button
        val bottomSheet = findViewById<View>(R.id.bottom_sheet)
        val a = intent.getStringExtra("btitle")
        val b = intent.getStringExtra("image")
        val c = intent.getStringExtra("bctgry")
        current_bookid = intent.getStringExtra("book_id")
        Glide.with(this).load(b).into(img)
        t.text = a
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        mTextViewState = findViewById(R.id.text_view_state)
        mBottomSheetBehavior!!.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> mTextViewState!!.setText("Collapsed")
                    BottomSheetBehavior.STATE_EXPANDED -> mTextViewState!!.setText("Confirm Order?")
                    BottomSheetBehavior.STATE_HIDDEN -> mTextViewState!!.setText("Hidden")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                mTextViewState!!.setText("What Do You Think")
            }
        })
        purchase_request.setOnClickListener { mBottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED) }
        findViewById<View>(R.id.btn).setOnClickListener { //Toast.makeText(getApplicationContext(), "hkwjenwj",Toast.LENGTH_LONG).show();
            request_refrence = FirebaseDatabase.getInstance().getReference("PurchaseRequest/" + current_bookid + "/" + user!!.uid)
            request_refrence!!.setValue(true)
        }
    }
}