package com.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.ForgetPassword
import com.booxapp.SellBookDetails
import com.booxapp.databinding.BookmarkItemBinding
import com.booxapp.databinding.OneRowBinding
import com.booxapp.model.BookModel
import com.bumptech.glide.Glide

class BookmarkAdapter(private val context: Context, val myDataModel: ArrayList<BookModel>) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BookmarkItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int) {
        holder.bind(context, myDataModel[position])
    }

    override fun getItemCount(): Int {
        return myDataModel.size
    }

    inner class ViewHolder(private val binding: BookmarkItemBinding) :
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

                val i = Intent(context, ForgetPassword::class.java)
                i.putExtras(bundle)
                context.startActivity(i)

            })
        }
    }

}