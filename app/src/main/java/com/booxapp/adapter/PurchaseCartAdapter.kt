package com.booxapp.adapter

import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.R
import com.booxapp.model.BookModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class PurchaseCartAdapter() : RecyclerView.Adapter<PurchaseCartAdapter.ViewHolder>() {

    lateinit var cartcontext: Context
    lateinit var cartvalues: ArrayList<BookModel>

    protected var cartlistener: ItemListener? = null
    var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()

    constructor(
            cartContext: Context,
            cartValues: ArrayList<BookModel>
    ):this(){
        this.cartcontext = cartcontext
        this.cartvalues = cartvalues
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(cartcontext).inflate(R.layout.purchase_cart_layout, parent, false))
    }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {

         holder.bind(cartvalues[position])
    }

    public override fun getItemCount(): Int {
        return cartvalues.size
    }

    inner class ViewHolder constructor(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var cartbook_name: TextView = v.findViewById(R.id.cart_title)
        var cartbook_category: TextView = v.findViewById(R.id.cart_ctgry)
        var cartbook_price: TextView = v.findViewById(R.id.cart_price)
        var cartbookmark: ImageView = v.findViewById(R.id.cart_bookmark)
        var cartbook_img: ImageView? = null
        var item: BookModel? = null

        fun bind(bookModel: BookModel) {

            cartbook_name.text = bookModel.title
            cartbook_category.text = bookModel.category
            cartbook_price.text = bookModel.offeredprice

            //Glide.with(mContext).load(item.getImagelink()).into(imageView);

        }

         override fun onClick(view: View) {
            if (cartcontext != null) {
                cartlistener!!.onItemClick(item)
            }
        }


    }



    open interface ItemListener {
        fun onItemClick(item: BookModel?)
    }
}