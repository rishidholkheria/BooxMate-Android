package com.booxapp.BookBuzz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.R
import com.booxapp.SellBookDetails
import com.booxapp.databinding.ActivityBookBuzzBinding
import com.booxapp.databinding.BookBuzzItemBinding
import com.booxapp.databinding.OneRowBinding
import com.booxapp.model.BookModel
import com.bumptech.glide.Glide

class BookBuzzAdapter (private var titles: List<String>,
                       private var details: List<String>,
                       private var images: List<String>) :
    RecyclerView.Adapter<BookBuzzAdapter.BookBuzzViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookBuzzViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.book_buzz_item, parent, false)
        return BookBuzzViewHolder(v)

    }

    override fun onBindViewHolder(holder: BookBuzzViewHolder, position: Int) {
        holder.title.text = titles[position]
        holder.description.text = details[position]

        Glide.with(holder.image)
            .load(images[position])
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class BookBuzzViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.bb_title)
        val description: TextView = itemView.findViewById(R.id.bb_description)
        val image: ImageView = itemView.findViewById(R.id.bb_image)

//        var item: BookModel? = null
//        fun bind(context: Context, article: Article) {
//            binding.bbTitle.text = article.title
//            binding.bbDescription.text = article.description
//
//            Glide.with(context)
//                .load(article.urlToImage)
//                .into(binding.bbImage);
//        }


    }
}