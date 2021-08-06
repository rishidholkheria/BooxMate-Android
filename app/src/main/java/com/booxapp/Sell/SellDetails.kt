package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.SellDetails
import com.booxapp.databinding.ActivitySellDetailsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SellDetails : AppCompatActivity() {

    private var bundle: Bundle? = null
    lateinit var binding: ActivitySellDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = Bundle()

        binding.moreBookDetailsBtn!!.setOnClickListener(View.OnClickListener {
            val title = binding.title!!.getText().toString()
            val desc = binding.description!!.getText().toString()
            val cat = binding.category!!.getSelectedItem().toString()

            if (binding.title!!.length() == 0) {
                binding.title!!.setError("Can't be Empty")
            }
            if (binding.description!!.length() == 0) {
                binding.description!!.setError("Can't be Empty")
            }
            if (title.length != 0 && desc.length != 0 && cat.length != 0) {
                bundle!!.putString("sbookname", title)
                bundle!!.putString("sbookdesc", desc)
                bundle!!.putString("sbookcat", cat)

                val intent = Intent(this@SellDetails, BookPhoto::class.java)
                intent.putExtras(bundle!!)
                startActivity(intent)

            }
        })
    }
}