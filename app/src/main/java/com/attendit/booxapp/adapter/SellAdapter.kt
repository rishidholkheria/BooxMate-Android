package com.attendit.booxapp.adapter

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.attendit.booxapp.R
import com.attendit.booxapp.SellBookDetails
import com.attendit.booxapp.model.BookModel
import com.attendit.booxapp.model.SellModel
import com.attendit.booxapp.utils.layoutInflater
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class SellAdapter() : RecyclerView.Adapter<SellAdapter.ViewHolder>() {


    lateinit var myContext: Context
    lateinit var myValues: ArrayList<BookModel>

    protected var myListener: ItemListener? = null


    constructor(context: Context, values: ArrayList<BookModel>
    ) : this() {
        this.myContext = context
        this.myValues = values
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(myContext).inflate(R.layout.past_order, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myValues[position])
    }

    public override fun getItemCount(): Int {
        return myValues.size
    }

    inner class ViewHolder constructor(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var book_name: TextView = v.findViewById(R.id.bookname)
        var book_status: TextView = v.findViewById(R.id.book_status)
        var book_price: TextView = v.findViewById(R.id.price)

        var item: BookModel? = null

        fun bind(bookModel: BookModel) {
            book_name.text = bookModel.title
//            book_status.text = sellModel.sellstatus
            book_price.text = bookModel.offeredprice
        }

        override fun onClick(view: View) {
            if (myListener != null) {
                myListener!!.onItemClick(item)
            }
        }

        init {
            v.setOnClickListener(object : View.OnClickListener {
                public override fun onClick(v: View) {
                    val i: Intent = Intent(myContext, SellBookDetails::class.java)
                    i.putExtra("btitle", book_name.getText().toString().trim({ it <= ' ' }))
//                    i.putExtra("image", item!!.bookimageLink)
                    myContext.startActivity(i)
                }
            })

        }
    }


    open interface ItemListener {
        fun onItemClick(item: BookModel?)
    }
}