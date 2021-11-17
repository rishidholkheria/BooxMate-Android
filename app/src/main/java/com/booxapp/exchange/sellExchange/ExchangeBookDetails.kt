package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityBookDetailsBinding
import com.booxapp.databinding.ExchangeBookDetailsBinding
import com.booxapp.databinding.OnSaleBookDetailsBinding
import com.booxapp.databinding.OnSellExchangeBookDetailsBinding
import com.booxapp.exchange.sellExchange.ExchangeViewRequests
import com.booxapp.purchase.BookmarkedBooks
import com.booxapp.sell.ViewRequests
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class ExchangeBookDetails : AppCompatActivity() {

    lateinit var binding: OnSellExchangeBookDetailsBinding

    var uDatabase: DatabaseReference? =
        FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

    var eDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.EX_DB_NAME)

    lateinit var uid: String
    lateinit var tid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnSellExchangeBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        binding.bookName.text = bundle!!.getString("booktitle", "Book Title")
        binding.bookCtgry.text = bundle!!.getString("ctgry", "Category")
        binding.bookLoc.text = bundle!!.getString("location", "Book Title")
        binding.bookCity.text = bundle!!.getString("city", "City")
        binding.bookMrp.text = bundle!!.getString("mrp", "Rs ")
        binding.expectedBooks.text = bundle!!.getString("expectedBooks", "No books available")
        binding.bookDesc.text = bundle!!.getString("booktitle", "No description available.")
        Glide.with(this)
            .load(bundle!!.getString("image", "No Image"))
            .into(binding.bookImage);

        var exBookId = bundle!!.getString("exBookId")
//        Log.e("ExViewRequests1",exBookId.toString())

        tid = Prefs.getStringPrefs(
            applicationContext,
            "Id"
        ).toString()

        uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        ).toString()


        checkIfSold(exBookId.toString())

        binding.exViewRequests.setOnClickListener(View.OnClickListener {
            val i = Intent(this, ExchangeViewRequests::class.java)
            i.putExtra("exBookId", exBookId)
//            Log.e("ExViewRequests2",exBookId.toString())
            startActivity(i)
        })
    }

    private fun checkIfSold(bId: String) {
        eDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    if (child.child("id").value.toString() == bId && child.child("status").value == true) {
                        binding.exViewRequests.isClickable = false
                        binding.exViewRequests.setText("Book Sold")
                        break;
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}