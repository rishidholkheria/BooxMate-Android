package com.booxapp.store

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.booxapp.Constants
import com.booxapp.ExchangeBookDetails
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityBookDetailsBinding
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlin.math.log

class BooxPurchaseDetails : AppCompatActivity() {
    lateinit var binding: ActivityBookDetailsBinding

    var buyerDatabase: DatabaseReference? =
        FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

    var bookDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.BOOXSTORE)

    lateinit var uid: String
    lateinit var tid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        binding.bookName.text = bundle!!.getString("booktitle", "Book Title")
        binding.bookOfferedPrice.text = bundle!!.getString("oprice", "Rs ")
        binding.bookCtgry.text = bundle!!.getString("ctgry", "Category")
        binding.bookMrp.text = bundle!!.getString("mrp", "Rs ")
        binding.bookOp.text = bundle!!.getString("oprice", "Rs ")
//        binding.stock.text = bundle!!.getString("stock", "SOld out ")
        binding.bookDesc.text = bundle!!.getString("desc", "No description available.")
        Glide.with(this)
            .load(bundle!!.getString("image", "No Image"))
            .into(binding.bookImage);
        var bId = bundle!!.getString("bookid")
        var bTitle = bundle!!.getString("booktitle")


        tid = Prefs.getStringPrefs(
            applicationContext,
            "Id"
        ).toString()

        uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        ).toString()

        binding.request.text = "BUY NOW"
        binding.request.setOnClickListener {
            val intent = Intent(applicationContext, CheckoutDetails::class.java)
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
        }
    }
}