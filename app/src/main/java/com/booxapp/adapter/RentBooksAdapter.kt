package com.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.BookDetails
import com.booxapp.R
import com.booxapp.model.RentBookModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*

class RentBooksAdapter() : RecyclerView.Adapter<RentBooksAdapter.ViewHolder>() {

    lateinit var rbccontext: Context
    lateinit var rbvalues: ArrayList<RentBookModel?>

    protected var rbListener: ItemListener? = null
    private var rentfirebaseUser: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()

    constructor(
            rbccontext: Context,
            rbvalues: ArrayList<RentBookModel?>
    ):this(){
        this.rbccontext = rbccontext
        this.rbvalues = rbvalues
    }


    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(rbccontext).inflate(R.layout.rent_purchase_layout, parent, false)
        return ViewHolder(view)
    }

    public override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rbvalues[position])
    }

    public override fun getItemCount(): Int {
        return rbvalues.size
    }

    inner class ViewHolder constructor(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var book_name: TextView  = v.findViewById(R.id.rbookname)
        var book_category: TextView = v.findViewById(R.id.rentcategory)
        var book_price: TextView = v.findViewById(R.id.rentprice)
        var bookmark: ImageView = v.findViewById(R.id.rentbookimage)
        var book_img: ImageView = v.findViewById(R.id.rentbookmark)
        var item: RentBookModel? = null

        fun bind(rentBookModel: RentBookModel?){
            book_name.text = rentBookModel!!.title
            book_category.text = rentBookModel!!.category
            book_price.text = rentBookModel!!.offeredprice
        }


//        fun setData(item: RentBookModel) {
//            this.item = item
//            book_name.setText(item.title)
//            book_category.setText(item.category)
//            book_price.setText(item.offeredprice)
//            //Glide.with(rbccontext).load(item.getImagelink()).into(book_img);
//        }

        override fun onClick(view: View) {
            if (rbccontext != null) {
                rbListener!!.onItemClick(item)
            }
        }

        init {
            v.setOnClickListener(object : View.OnClickListener {
                public override fun onClick(v: View) {
                    val i: Intent = Intent(rbccontext, BookDetails::class.java)
                    i.putExtra("btitle", book_name.getText().toString().trim({ it <= ' ' }))
                    i.putExtra("bctgry", book_name.getText().toString().trim({ it <= ' ' }))
                    i.putExtra("image", item!!.imagelink)
                    rbccontext!!.startActivity(i)
                }
            })

        }
    }


    open interface ItemListener {
        fun onItemClick(item: RentBookModel?)
    }

    private fun isSaved(bookID: String, imageView: ImageView) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("RentBookmarked").child(rentfirebaseUser!!.getUid())
        reference.addValueEventListener(object : ValueEventListener {
            public override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(bookID).exists()) {
                    imageView.setImageResource(R.drawable.ic_selectedbookmark)
                    imageView.setTag("rentbookmarked")
                } else {
                    imageView.setImageResource(R.drawable.ic_bookmark)
                    imageView.setTag("rentbookmark")
                }
            }

            public override fun onCancelled(error: DatabaseError) {}
        })
    }
}