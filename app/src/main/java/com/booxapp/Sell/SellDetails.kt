package com.booxapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.booxapp.Sell.BookImages
import com.booxapp.Sell.ConfirmPublishFragment
import com.booxapp.Sell.PublishDetails
import com.booxapp.databinding.ActivitySellDetailsBinding
import com.booxapp.model.BookModel


class SellDetails : AppCompatActivity(), ShareData {

    lateinit var binding: ActivitySellDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySellDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(PublishDetails())

//        binding.moreBookDetailsBtn!!.setOnClickListener(View.OnClickListener {
//            val title = binding.title!!.getText().toString()
//            val desc = binding.description!!.getText().toString()
//            val cat = binding.category!!.getSelectedItem().toString()
//
//            if (binding.title!!.length() == 0) {
//                binding.title!!.setError("Can't be Empty")
//            }
//            if (binding.description!!.length() == 0) {
//                binding.description!!.setError("Can't be Empty")
//            }
//            if (title.length != 0 && desc.length != 0 && cat.length != 0) {
//                bundle!!.putString("sbookname", title)
//                bundle!!.putString("sbookdesc", desc)
//                bundle!!.putString("sbookcat", cat)
//
//                val intent = Intent(this@SellDetails, BookPhoto::class.java)
//                intent.putExtras(bundle!!)
//                startActivity(intent)
//
//            }
//        })

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.publish_fragments_container, fragment)
        fragmentTransaction.commit()
    }

    override fun passingData(choice: Int, bookModel1: BookModel?) {
        var bookmodel1Var: BookModel
        if (choice == 1) {
            bookmodel1Var = bookModel1!!
            Toast.makeText(applicationContext, "I was here", Toast.LENGTH_SHORT).show()
            replaceFragment(BookImages())
        } else if(choice == 2){
            replaceFragment(ConfirmPublishFragment())
        }
    }

}