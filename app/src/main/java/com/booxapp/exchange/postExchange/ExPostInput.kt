package com.booxapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.booxapp.databinding.ActivityExPostInputBinding
import com.booxapp.databinding.ActivitySellDetailsBinding
import com.booxapp.exchange.postExchange.BookImagesExFragment
import com.booxapp.exchange.postExchange.ExPostDetailFragment
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel
import com.booxapp.sell.BookImages
import com.booxapp.sell.OrderSuccessfullFragment
import com.booxapp.sell.PublishDetails


class ExPostInput : AppCompatActivity(), ExShareData {

    lateinit var binding: ActivityExPostInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExPostInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(ExPostDetailFragment(), null)
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