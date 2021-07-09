package com.attendit.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.attendit.booxapp.BookDetails
import com.attendit.booxapp.R
import com.attendit.booxapp.model.BookModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter() : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var myDataModel: ArrayList<BookModel>
    protected var mListener: ItemListener? = null
    private var firebaseUser = FirebaseAuth.getInstance().currentUser

    constructor(
            context: Context,
            myDataModel: ArrayList<BookModel>
    ) : this() {
        this.context = context
        this.myDataModel = myDataModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.one_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {

        holder.bind(myDataModel[position])

    }

    override fun getItemCount(): Int {
        return myDataModel.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        val tv_title: TextView = v.findViewById(R.id.tv_title)
        val tv_location: TextView = v.findViewById(R.id.tv_location)
        val tv_price: TextView = v.findViewById(R.id.tv_price)
        val iv_bookmark: ImageView = v.findViewById(R.id.iv_bookmark)
        val imageView: ImageView = v.findViewById(R.id.imageView)

        var item: BookModel? = null

        fun bind(bookModel: BookModel) {
            tv_title.text = bookModel!!.title
            tv_location.text = bookModel!!.location
            tv_price.text = bookModel!!.offeredprice
        }


        override fun onClick(view: View) {
            if (mListener != null) {
//                mListener!!.onItemClick(item);
            }
        }


    }


    interface ItemListener {
        fun onItemClick(item: BookModel)
    }

    private fun isSaved(bookID: String, imageView: ImageView) {
        val reference = FirebaseDatabase.getInstance().reference.child("Bookmarked").child(firebaseUser!!.uid)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(bookID).exists()) {
                    imageView.setImageResource(R.drawable.ic_selectedbookmark)
                    imageView.tag = "bookmarked"
                } else {
                    imageView.setImageResource(R.drawable.ic_bookmark)
                    imageView.tag = "bookmark"
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}