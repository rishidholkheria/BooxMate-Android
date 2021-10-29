package com.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.ExPurchaseDetails
import com.booxapp.ExchangeBookDetails
import com.booxapp.SellBookDetails
import com.booxapp.databinding.OneRowBinding
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlin.collections.ArrayList

class PostExchangeAdapter(private val context: Context, val DataModel: ArrayList<ExchangeModel>) :
    RecyclerView.Adapter<PostExchangeAdapter.ViewHolder>() {

    private var firebaseUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OneRowBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostExchangeAdapter.ViewHolder, position: Int) {
        holder.bind(context, DataModel[position])
    }

    override fun getItemCount(): Int {
        return DataModel.size
    }

    inner class ViewHolder(private val binding: OneRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var item: BookModel? = null
        fun bind(context: Context, exModel: ExchangeModel) {
            binding.tvTitle.text = exModel.title
            binding.tvLocation.text = exModel.location
            binding.tvCity.text = exModel.city
            binding.tvCat.text = exModel.category

            Glide.with(context)
                .load(exModel.imagelink)
                .into(binding.imageView);

            binding.root.setOnClickListener(View.OnClickListener {
                val bundle = Bundle()
                bundle.putString("booktitle", exModel.title)
                bundle.putString("location", exModel.location)
                bundle.putString("city", exModel.city)
                bundle.putString("ctgry", exModel.category)
                bundle.putString("desc", exModel.description)
                bundle.putString("image", exModel.imagelink)
                bundle.putString("exBookId", exModel.id)

                var intent = Intent(context, ExchangeBookDetails::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            })
        }
    }

}