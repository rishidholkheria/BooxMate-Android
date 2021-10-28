package com.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.R
import com.booxapp.SellBookDetails
import com.booxapp.databinding.PastOrderBinding
import com.booxapp.model.BookModel
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList

class SellAdapter(private val context: Context, val DataModel: ArrayList<BookModel>) :
    RecyclerView.Adapter<SellAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PastOrderBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SellAdapter.ViewHolder, position: Int) {
        holder.bind(context, DataModel[position])
    }

    override fun getItemCount(): Int {
        return DataModel.size
    }

    inner class ViewHolder(private val binding: PastOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var item: BookModel? = null
        fun bind(context: Context, bookModel: BookModel) {
            binding.bookname.text = bookModel.title
            binding.location.text = bookModel.location
            binding.price.text = bookModel.offeredprice
            binding.reqCount.text = bookModel.requests.size.toString()

            if(bookModel.status == true){
                binding.bookStatus.text = "Sold"
                binding.bookStatus.setBackgroundResource(R.color.purple)
            }

            Glide.with(context)
                .load(bookModel.imagelink)
                .into(binding.bookimage);

            binding.root.setOnClickListener(View.OnClickListener {
                val bundle = Bundle()
                bundle.putString("booktitle", bookModel.title)
                bundle.putString("oprice", bookModel.offeredprice)
                bundle.putString("mrp", bookModel.mrp)
                bundle.putString("location", bookModel.location)
                bundle.putString("ctgry", bookModel.category)
                bundle.putString("desc", bookModel.description)
                bundle.putString("image", bookModel.imagelink)
                bundle.putString("bookid", bookModel.id)

                var intent = Intent(context, SellBookDetails::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            })
        }
    }
}

