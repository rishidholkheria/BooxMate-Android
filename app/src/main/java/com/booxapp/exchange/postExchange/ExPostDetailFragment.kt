package com.booxapp.exchange.postExchange

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.booxapp.ExShareData
import com.booxapp.ShareData
import com.booxapp.databinding.ExPostDetailFragmentBinding
import com.booxapp.databinding.FragmentExPostBinding
import com.booxapp.databinding.FragmentExchangeBinding
import com.booxapp.databinding.FragmentPublishDetailsBinding
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel


class ExPostDetailFragment : Fragment() {
    lateinit var binding: ExPostDetailFragmentBinding
    lateinit var shareData: ExShareData

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            shareData = activity as ExShareData
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement ShareData")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExPostDetailFragmentBinding.inflate(inflater, container, false)

        binding.bookPhotoFab.setOnClickListener(View.OnClickListener {
            val ebook_name = binding.title!!.text.toString()
            val ebook_desc = binding.description!!.text.toString()
            val ebook_cat = binding.category!!.selectedItem.toString()
            val ebook_expBooks = binding.expectedBooks!!.text.toString()
            val ebook_loc = binding.location!!.text.toString()

                if (isDataValid(ebook_name) && isDataValid(ebook_desc) && isDataValid(ebook_cat) && isDataValid(
                        ebook_expBooks
                    ) && isDataValid(ebook_loc)
                ) {
                    shareData.passingData(
                        1,
                        ExchangeModel(ebook_name, ebook_expBooks, ebook_desc, ebook_cat, ebook_loc)
                    )
                } else {
                    Toast.makeText(activity, "incomplete", Toast.LENGTH_SHORT).show()
                }
        })

        return binding.root
    }


    private fun isDataValid(data: String): Boolean {
        if (data.isEmpty()) {
            return false
        }
        return true
    }

}