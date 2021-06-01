package com.attendit.booxapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.attendit.booxapp.SellDetails
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SellDetails : AppCompatActivity() {
    private var btitle: EditText? = null
    private var bdesc: EditText? = null
    private var bcategory: Spinner? = null
    private var bundle: Bundle? = null
    private var proceedfab: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_details)
        btitle = findViewById(R.id.book_title)
        bdesc = findViewById(R.id.book_description)
        bcategory = findViewById(R.id.category)
        bundle = Bundle()
        proceedfab = findViewById(R.id.nextfab1)
        proceedfab!!.setOnClickListener(View.OnClickListener {
            val b_title = btitle!!.getText().toString()
            val b_desc = bdesc!!.getText().toString()
            val b_cat = bcategory!!.getSelectedItem().toString()
            if (btitle!!.length() == 0) {
                btitle!!.setError("Can't be Empty")
            }
            if (bdesc!!.length() == 0) {
                bdesc!!.setError("Can't be Empty")
            }
            if (b_title.length != 0 && b_desc.length != 0 && b_cat.length != 0) {
                bundle!!.putString("bk_name", b_title)
                bundle!!.putString("bk_desc", b_desc)
                bundle!!.putString("bk_cat", b_cat)
                val intent = Intent(this@SellDetails, BookPhoto::class.java)
                intent.putExtras(bundle!!)
                startActivity(intent)
            }
        })
    }
}