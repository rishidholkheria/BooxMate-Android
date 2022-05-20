package com.booxapp.store

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.booxapp.BxShareData
import com.booxapp.R
import com.booxapp.databinding.ActivitySellDetailsBinding
import com.booxapp.model.BooxstoreModel
import com.booxapp.sell.OrderSuccessfullFragment
import com.booxapp.sell.PublishDetails


class CheckoutDetails : AppCompatActivity(), BxShareData {

    lateinit var binding: ActivitySellDetailsBinding
    lateinit var bookId: String
    lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySellDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toolbar)

        binding.toolbarLayout.toolbar.overflowIcon?.setTint(Color.WHITE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //For back btn
        supportActionBar?.setDisplayShowHomeEnabled(true) //Both lines for back btn

        binding.fragmentBuyerContainer.visibility = View.VISIBLE

        bundle = intent.extras!!
        Log.e("Bundle",bundle.toString())
        replaceFragment(CheckoutFragment(), null)

    }

    //for back btn toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, booxstoreModel: BooxstoreModel?) {
        if (booxstoreModel != null) {
            val newBundle = Bundle();
            newBundle.putParcelable(
                "bookModel", booxstoreModel,
            )
            newBundle.putParcelable(
                "bookBundle", bundle
            )
            fragment.arguments = newBundle
            Log.e("Booxstore",bundle.toString()+ "   " + booxstoreModel.toString())
        }
       else{
            Log.e("Booxstore", "null")
       }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_buyer_container, fragment).commit()
    }

    override fun passingData(choice: Int, bookModel: BooxstoreModel?) {
        if (choice == 1) {
            replaceFragment(BooxStoreFragment(), bookModel)
        } else if (choice == 2) {
            replaceFragment(BxOrderSuccessful(), null)
        }
    }

}