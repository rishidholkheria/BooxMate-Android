package com.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.BookDetails
import com.booxapp.PurchaseDetails
import com.booxapp.R
import com.booxapp.SellBookDetails
import com.booxapp.data.Prefs
import com.booxapp.databinding.FragmentPurchaseBinding
import com.booxapp.databinding.FragmentSellBinding
import com.booxapp.databinding.OneRowBinding
import com.booxapp.model.BookModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter(private val context: Context, val myDataModel: ArrayList<BookModel>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

//    private var firebaseUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OneRowBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.bind(context, myDataModel[position])
    }

    override fun getItemCount(): Int {
        return myDataModel.size
    }

    inner class ViewHolder(private val binding: OneRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var item: BookModel? = null
        fun bind(context: Context, bookModel: BookModel) {
            binding.tvTitle.text = bookModel.title
            binding.tvLocation.text = bookModel.location
            binding.tvPrice.text = bookModel.offeredprice
            binding.tvCat.text = bookModel.category
            Glide.with(context)
                .load(bookModel.imagelink)
                .into(binding.imageView);

            binding.root.setOnClickListener(View.OnClickListener {
                val bundle = Bundle()
                bundle.putString("booktitle", bookModel.title!!)
                bundle.putString("oprice", bookModel.offeredprice!!)
                bundle.putString("mrp", bookModel.mrp!!)
                bundle.putString("location", bookModel.location!!)
                bundle.putString("ctgry", bookModel.category!!)
                bundle.putString("desc", bookModel.description!!)
                bundle.putString("image", bookModel.imagelink!!)
                bundle.putString("bookid", bookModel.id!!)

                var intent = Intent(context, PurchaseDetails::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            })
        }
    }

}