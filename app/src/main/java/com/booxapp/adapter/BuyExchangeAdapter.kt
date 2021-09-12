package com.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.SellBookDetails
import com.booxapp.databinding.OneRowBinding
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlin.collections.ArrayList

class BuyExchangeAdapter(private val context: Context, val DataModel: ArrayList<ExchangeModel>) :
    RecyclerView.Adapter<BuyExchangeAdapter.ViewHolder>() {

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

    override fun onBindViewHolder(holder: BuyExchangeAdapter.ViewHolder, position: Int) {
        holder.bind(context, DataModel[position])
    }

    override fun getItemCount(): Int {
        return DataModel.size
    }

    inner class ViewHolder(private val binding: OneRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var item: ExchangeModel? = null
        fun bind(context: Context, exModel: ExchangeModel) {
            binding.tvTitle.text = exModel.title
            binding.tvLocation.text = exModel.location
//            Glide.with(context)
//                .load(exModel.imagelink)
//                .into(binding.imageView);

            binding.root.setOnClickListener(View.OnClickListener {
                val bundle = Bundle()
                bundle.putString("booktitle", exModel.title)
                bundle.putString("location", exModel.location)
                bundle.putString("ctgry", exModel.category)
                bundle.putString("desc", exModel.description)
//                bundle.putString("image", exModel.imagelink)

                var intent = Intent(context, SellBookDetails::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            })
        }
    }


//    private fun isSaved(bookID: String, imageView: ImageView) {
//        val reference =
//            FirebaseDatabase.getInstance().reference.child("Bookmarked").child(firebaseUser!!.uid)
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.child(bookID).exists()) {
//                    imageView.setImageResource(R.drawable.ic_selectedbookmark)
//                    imageView.tag = "bookmarked"
//                } else {
//                    imageView.setImageResource(R.drawable.ic_bookmark)
//                    imageView.tag = "bookmark"
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {}
//        })
//    }
}