package com.booxapp

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.booxapp.databinding.ActivitySellDetailsBinding
import com.booxapp.model.BookModel
import com.booxapp.sell.BookImages
import com.booxapp.sell.OrderSuccessfullFragment
import com.booxapp.sell.PublishDetails


class SellDetails : AppCompatActivity(), ShareData {

    lateinit var binding: ActivitySellDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySellDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toobar)

        binding.toolbarLayout.toobar.overflowIcon?.setTint(Color.WHITE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //For back btn
        supportActionBar?.setDisplayShowHomeEnabled(true) //Both lines for back btn

        replaceFragment(PublishDetails(), null)
    }

    //for back btn toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun replaceFragment(fragment: Fragment, bookModel: BookModel?) {
        if (bookModel != null) {
            val bundle = Bundle()
            bundle.putParcelable(
                "bookModel", bookModel
            )
            fragment.arguments = bundle
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.publish_fragments_container, fragment).commit()
    }

    override fun passingData(choice: Int, bookModel: BookModel?) {
        if (choice == 1) {
            replaceFragment(BookImages(), bookModel)
        } else if (choice == 2) {
            replaceFragment(OrderSuccessfullFragment(), null)
        }
    }

}