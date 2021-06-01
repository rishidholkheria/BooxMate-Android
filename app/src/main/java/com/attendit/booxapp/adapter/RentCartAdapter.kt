package com.attendit.booxapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.attendit.booxapp.BookDetails
import com.attendit.booxapp.R
import com.attendit.booxapp.model.RentBookModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class RentCartAdapter constructor(private val rccontext: Context?, private val rcvalues: ArrayList<RentBookModel?>) : RecyclerView.Adapter<RentCartAdapter.ViewHolder>() {
    protected var rcListner: ItemListener? = null
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()

    inner class ViewHolder constructor(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var book_name: TextView
        var book_category: TextView
        var book_price: TextView
        var bookmark: ImageView
        var book_img: ImageView
        var item: RentBookModel? = null
        fun setData(item: RentBookModel?) {
            this.item = item
            book_name.setText(item!!.title)
            book_category.setText(item!!.category)
            book_price.setText(item!!.totalprice)
            Glide.with((rccontext)!!).load(item!!.imagelink).into(book_img)
        }

        public override fun onClick(view: View) {
            if (rccontext != null) {
                rcListner!!.onItemClick(item)
            }
        }

        init {
            book_name = v.findViewById(R.id.rcbookname)
            book_category = v.findViewById(R.id.rentcartcategory)
            book_price = v.findViewById(R.id.rentcartprice)
            book_img = v.findViewById(R.id.rentcartbookimage)
            bookmark = v.findViewById(R.id.rentcartbookmark)

            v.setOnClickListener(object : View.OnClickListener {
                public override fun onClick(v: View) {
                    val i: Intent = Intent(rccontext, BookDetails::class.java)
                    i.putExtra("btitle", book_name.getText().toString().trim({ it <= ' ' }))
                    i.putExtra("bctgry", book_name.getText().toString().trim({ it <= ' ' }))
                    i.putExtra("image", item!!.imagelink)
                    rccontext!!.startActivity(i)
                }
            })

        }
    }

    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(rccontext).inflate(R.layout.rent_cart_layout, parent, false)
        return ViewHolder(view)
    }

    public override fun onBindViewHolder(Vholder: ViewHolder, position: Int) {
        Vholder.setData(rcvalues.get(position))
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser()
        val bookmark: RentBookModel? = rcvalues.get(position)
        //isSaved(bookmark.getId(), Vholder.iv_bookmark);
        Vholder.bookmark.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                var bid: String = bookmark?.id ?: ""
                FirebaseDatabase.getInstance().getReference("RentBookmarked").child(firebaseUser!!.getUid()).child(bid).removeValue()
            }
        })
    }

    public override fun getItemCount(): Int {
        return rcvalues.size
    }

    open interface ItemListener {
        fun onItemClick(item: RentBookModel?)
    }
}