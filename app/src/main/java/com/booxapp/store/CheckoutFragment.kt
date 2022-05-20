package com.booxapp.store

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.booxapp.BxShareData
import com.booxapp.databinding.FragmentBuyerDetailsBinding
import com.booxapp.databinding.FragmentPublishDetailsBinding
import com.booxapp.model.BooxstoreModel


class CheckoutFragment : Fragment() {
    lateinit var binding: FragmentBuyerDetailsBinding
    lateinit var shareData: BxShareData

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            shareData = activity as BxShareData
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement ShareData")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuyerDetailsBinding.inflate(inflater, container, false)

        binding.bookPhotoFab!!.setOnClickListener(View.OnClickListener {
            val buyerName = binding.buyerName!!.text.toString()
            val buyerAddress = binding.buyerAddress!!.text.toString()
            val buyerContact = binding.buyerContact!!.text.toString()

            if (isDataValid(buyerName) && isDataValid(buyerAddress) && isDataValid(buyerContact)) {
                shareData.passingData(
                    1,
                    BooxstoreModel(buyerName, buyerAddress, buyerContact)
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