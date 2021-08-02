package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RentSellBooks : Fragment() {
    var nextfab: FloatingActionButton? = null
    var rbooktitle: EditText? = null
    var rbookdesc: EditText? = null
    var rbooktime: EditText? = null
    var bundle: Bundle? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rent_sell_books, container, false)
        rbooktitle = view.findViewById(R.id.rent_book_name)
        rbookdesc = view.findViewById(R.id.rent_book_desc)
        rbooktime = view.findViewById(R.id.rent_time_prd)
        nextfab = view.findViewById(R.id.rentsellnext)
        bundle = Bundle()
        nextfab!!.setOnClickListener(View.OnClickListener {
            val rb_title = rbooktitle!!.getText().toString()
            val rb_desc = rbookdesc!!.getText().toString()
            val rb_time = rbooktime!!.getText().toString()
            if (rbooktitle!!.length() == 0) {
                rbooktitle!!.setError("Can't be Empty")
            }
            if (rbookdesc!!.length() == 0) {
                rbookdesc!!.setError("Can't be Empty")
            }
            if (rbooktime!!.length() == 0) {
                rbooktime!!.setError("Can't be Empty")
            }
            if (rb_title.length != 0 && rb_desc.length != 0 && rb_time.length != 0) {
                bundle!!.putString("rbook_title", rb_title)
                bundle!!.putString("rbook_desc", rb_desc)
                bundle!!.putString("rbook_time", rb_time)
                val intent = Intent(activity, RentBookPhoto::class.java)
                intent.putExtras(bundle!!)
                startActivity(intent)
            }
        })
        return view
    }
}