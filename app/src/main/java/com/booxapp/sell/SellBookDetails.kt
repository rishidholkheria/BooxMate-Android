package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityBookDetailsBinding
import com.booxapp.databinding.OnSaleBookDetailsBinding
import com.booxapp.purchase.BookmarkedBooks
import com.booxapp.sell.ViewRequests
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class SellBookDetails : AppCompatActivity() {

    lateinit var binding: OnSaleBookDetailsBinding

    var uDatabase: DatabaseReference? =
        FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

    var bDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)

    lateinit var uid: String
    lateinit var tid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnSaleBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        binding.bookName.text = bundle!!.getString("booktitle", "Book Title")
        binding.bookOfferedPrice.text = bundle!!.getString("oprice", "Rs ")
        binding.bookCtgry.text = bundle!!.getString("ctgry", "Category")
        binding.bookLoc.text = bundle!!.getString("location", "Book Title")
        binding.bookCity.text = bundle!!.getString("city", "City")
        binding.bookMrp.text = bundle!!.getString("mrp", "Rs ")
        binding.bookOp.text = bundle!!.getString("oprice", "Rs ")
        binding.bookDesc.text = bundle!!.getString("booktitle", "No description available.")

        Glide.with(this)
            .load(bundle!!.getString("image", "No Image"))
            .into(binding.bookImage);

        var bId = bundle!!.getString("bookid")

        tid = Prefs.getStringPrefs(
            applicationContext,
            "Id"
        ).toString()

        uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        ).toString()


        checkIfSold(bId.toString())

        binding.viewRequests.setOnClickListener(View.OnClickListener {
            val i = Intent(this, ViewRequests::class.java)
            i.putExtra("bookid", bId)
            startActivity(i)
        })
    }

    private fun checkIfSold(bId: String) {
            bDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (child in dataSnapshot.children) {
                        if (child.child("id").value.toString() == bId && child.child("status").value == true) {
                            binding.viewRequests.isClickable = false
                            binding.viewRequests.setText("Book Sold")
                            binding.buyerDetailsCv.isVisible = true
                            var buyers: ArrayList<String> = child.child("requests").value as ArrayList<String>
                            Log.e("Sell", buyers.toString())
                            var buyerId: String = buyers[0]  //temporary solution
                            getBuyerDetails(buyerId)

                            break;
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

    private fun getBuyerDetails(buyerId: String){
        uDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    if (child.child("id").value.toString() == buyerId ) {
                        binding.reqBuyerName.text = child.child("name").value.toString()
                        binding.reqBuyerLoc.text = child.child("loc").value.toString()
                        binding.reqBuyerPno.text = child.child("phone").value.toString()
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