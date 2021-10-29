package com.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.ExPurchaseDetails
import com.booxapp.R
import com.booxapp.SellBookDetails
import com.booxapp.databinding.OneRowBinding
import com.booxapp.databinding.PastOrderBinding
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList

class ExBookMarkAdapter(private val context: Context, val DataModel: ArrayList<ExchangeModel>) :
    RecyclerView.Adapter<ExBookMarkAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OneRowBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExBookMarkAdapter.ViewHolder, position: Int) {
        holder.bind(context, DataModel[position])
    }

    override fun getItemCount(): Int {
        return DataModel.size
    }

    inner class ViewHolder(private val binding: OneRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var item: BookModel? = null
        fun bind(context: Context, bookModel: ExchangeModel) {
            binding.tvTitle.text = bookModel.title
            binding.tvLocation.text = bookModel.location
            binding.tvCity.text = bookModel.city
            binding.tvCat.text = bookModel.category
            Glide.with(context)
                .load(bookModel.imagelink)
                .into(binding.imageView);

            binding.root.setOnClickListener(View.OnClickListener {
                val bundle = Bundle()
                bundle.putString("booktitle", bookModel.title)
                bundle.putString("location", bookModel.location)
                bundle.putString("city", bookModel.city)
                bundle.putString("ctgry", bookModel.category)
                bundle.putString("desc", bookModel.description)
                bundle.putString("image", bookModel.imagelink)
                bundle.putString("bookid", bookModel.id)

                var intent = Intent(context, ExPurchaseDetails::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            })
        }
    }
}

