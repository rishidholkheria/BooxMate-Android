package com.booxapp

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.booxapp.databinding.ActivityExPostInputBinding
import com.booxapp.exchange.sellExchange.BookImagesExFragment
import com.booxapp.exchange.sellExchange.ExPostDetailFragment
import com.booxapp.model.ExchangeModel
import com.booxapp.sell.OrderSuccessfullFragment


class ExPostInput : AppCompatActivity(), ExShareData {

    lateinit var binding: ActivityExPostInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExPostInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toolbar)

        binding.toolbarLayout.toolbar.overflowIcon?.setTint(Color.WHITE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //For back btn
        supportActionBar?.setDisplayShowHomeEnabled(true) //Both lines for back btn

        replaceFragment(ExPostDetailFragment(), null)
    }

    //for back btn toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, bookModel: ExchangeModel?) {
        if (bookModel != null) {
            val bundle = Bundle()
            bundle.putParcelable(
                "bookModel", bookModel
            )
            fragment.arguments = bundle
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.expost_frag_container, fragment).commit()
    }

    override fun passingData(choice: Int, bookModel: ExchangeModel?) {
        if (choice == 1) {
            replaceFragment(BookImagesExFragment(), bookModel)
        } else if (choice == 2) {
            replaceFragment(OrderSuccessfullFragment(), null)
        }
    }

}