package com.booxapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityBookDetailsBinding
import com.booxapp.databinding.ExchangeBookDetailsBinding
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class ExPurchaseDetails : AppCompatActivity() {
    lateinit var binding: ExchangeBookDetailsBinding

    var uDatabase: DatabaseReference? =
        FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

    var bDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)

    lateinit var sellerId: String

    lateinit var uid: String
    lateinit var tid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ExchangeBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        binding.bookName.text = bundle!!.getString("booktitle", "Book Title")
        binding.bookMrp.text = bundle!!.getString("mrp", "Rs ")
        binding.bookCtgry.text = bundle!!.getString("ctgry", "Category")
        binding.bookLoc.text = bundle!!.getString("location", "Book Title")
        binding.bookCity.text = bundle!!.getString("city", "City")
        binding.expectedBooks.text = bundle!!.getString("expectedBooks", "No book available ")
        binding.bookDesc.text = bundle!!.getString("booktitle", "No description available.")
        Glide.with(this)
            .load(bundle!!.getString("image", "No Image"))
            .into(binding.bookImage);

        var bId = bundle!!.getString("exBookId")
        Log.e("BOOOOOOOOOOOOK IDD", bId.toString())
        var bTitle = bundle!!.getString("booktitle")


        tid = Prefs.getStringPrefs(
            applicationContext,
            "Id"
        ).toString()

        uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        ).toString()


        binding.bookmark.setOnClickListener(View.OnClickListener {
            if (binding.bookmark.isChecked) {
                binding.bookmark.setBackgroundResource(R.drawable.ic_bookmark_selected1)
                FirebaseAdapter(applicationContext).eAddBookmark(
                    bId!!,
                    object : onCompleteFirebase {
                        override fun onCallback(value: Boolean) {
                            Toast.makeText(applicationContext, "Done", Toast.LENGTH_LONG).show()
                        }
                    })
            } else {
                binding.bookmark.setBackgroundResource(R.drawable.ic_bookmark1)
                deleteFromBookmarked(bId!!)
            }
        })

        binding.requestExchangeBtn.setOnClickListener(View.OnClickListener {
            FirebaseAdapter(applicationContext).eSaveBuyerId(bId!!, object : onCompleteFirebase {
                override fun onCallback(value: Boolean) {
                    Toast.makeText(applicationContext, "Done", Toast.LENGTH_LONG).show()
                }
            })

        })

    }

    fun deleteFromBookmarked(bookId: String) {
        uDatabase?.child(tid)?.child("exchangeBookmarkedBooks")
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (child in dataSnapshot.children) {
                        if (child.value.toString() == bookId) {
                            child.ref.removeValue()
                            Log.i("Deleted", bookId)
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