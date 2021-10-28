package com.booxapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityBookDetailsBinding
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class PurchaseDetails : AppCompatActivity() {
    lateinit var binding: ActivityBookDetailsBinding

    var uDatabase: DatabaseReference? =
        FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

    var bDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)

    lateinit var sellerId: String

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
        binding.bookLoc.text = bundle!!.getString("location", "Book Title")
        binding.bookMrp.text = bundle!!.getString("mrp", "Rs ")
        binding.bookOp.text = bundle!!.getString("oprice", "Rs ")
        binding.bookDesc.text = bundle!!.getString("booktitle", "No description available.")
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


        binding.bookmark.setOnClickListener(View.OnClickListener {
            if (binding.bookmark.isChecked) {
                binding.bookmark.setBackgroundResource(R.drawable.ic_bookmark_selected1)
                FirebaseAdapter(applicationContext).addBookmark(
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

        binding.request.setOnClickListener(View.OnClickListener {
            FirebaseAdapter(applicationContext).saveBuyerId(bId!!, object : onCompleteFirebase {
                override fun onCallback(value: Boolean) {
                    Toast.makeText(applicationContext, "Done", Toast.LENGTH_LONG).show()
                }
            })

        })

    }

    fun deleteFromBookmarked(bookId: String) {

        //----------------CHANGE KARNAA HAAIIIIII-------------------------------
//        var tid = Prefs.getStringPrefs(
//            applicationContext,
//            "Id"
//        ).toString()

        //---------------------------------------------

        uDatabase?.child(tid)?.child("bookmarkedBooks")
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