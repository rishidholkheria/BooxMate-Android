package com.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.PurchaseDetails
import com.booxapp.databinding.OneRowBinding
import com.booxapp.model.BookModel
import com.booxapp.model.BooxstoreModel
import com.booxapp.store.BooxPurchaseDetails
import com.booxapp.store.CheckoutDetails
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList

class BooxstoreAdapter(private val context: Context, val myDataModel: ArrayList<BooxstoreModel>) :
    RecyclerView.Adapter<BooxstoreAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OneRowBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BooxstoreAdapter.ViewHolder, position: Int) {
        holder.bind(context, myDataModel[position])
    }

    override fun getItemCount(): Int {
        return myDataModel.size
    }

    inner class ViewHolder(private val binding: OneRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var item: BooxstoreModel? = null
        fun bind(context: Context, bookModel: BooxstoreModel) {
            binding.tvTitle.text = bookModel.title
            binding.tvPrice.text = bookModel.offeredprice
            binding.tvCat.text = bookModel.category
            binding.tvLocation.text = "Located in "
            binding.tvCity.text = "New Delhi"
            binding.tvCom.visibility = View.GONE

                    Glide.with(context)
                .load(bookModel.imagelink)
                .into(binding.imageView);

            binding.root.setOnClickListener(View.OnClickListener {
                val bundle = Bundle()
                bundle.putString("booktitle", bookModel.title!!)
                bundle.putString("oprice", bookModel.offeredprice!!)
                bundle.putString("mrp", bookModel.mrp!!)
                bundle.putString("ctgry", bookModel.category!!)
                bundle.putString("desc", bookModel.description!!)
                bundle.putString("image", bookModel.imagelink!!)
                bundle.putString("bookid", bookModel.id!!)
                bundle.putString("stockCount",bookModel.stockCount!!)
                bundle.putBoolean("status",bookModel.status!!)

                var intent = Intent(context, BooxPurchaseDetails::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            })
        }
    }

}