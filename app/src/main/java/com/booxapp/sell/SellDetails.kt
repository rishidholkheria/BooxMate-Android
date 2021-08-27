package com.booxapp

import android.os.Bundle
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

        replaceFragment(PublishDetails(), null)

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