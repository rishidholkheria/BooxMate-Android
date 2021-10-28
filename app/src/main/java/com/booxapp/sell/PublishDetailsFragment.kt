package com.booxapp.sell

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.booxapp.ShareData
import com.booxapp.databinding.FragmentPublishDetailsBinding
import com.booxapp.model.BookModel


class PublishDetails : Fragment() {
    lateinit var binding: FragmentPublishDetailsBinding
    lateinit var shareData: ShareData

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            shareData = activity as ShareData
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement ShareData")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPublishDetailsBinding.inflate(inflater, container, false)

        binding.bookPhotoFab!!.setOnClickListener(View.OnClickListener {
            val sbook_name = binding.title!!.text.toString()
            val sbook_desc = binding.description!!.text.toString()
            val sbook_ctgry = binding.category!!.selectedItem.toString()
            val sbook_mrp = binding.mrp!!.text.toString()
            val sbook_op = binding.offeredPrice!!.text.toString()
            val sbook_loc = binding.location!!.text.toString()
            val sbook_city = binding.city!!.text.toString()

            if (isDataValid(sbook_name) && isDataValid(sbook_desc) && isDataValid(sbook_ctgry) && isDataValid(
                    sbook_mrp
                ) && isDataValid(sbook_op) && isDataValid(sbook_loc)
            ) {
                shareData.passingData(
                    1,
                    BookModel(sbook_name, sbook_loc, sbook_city , sbook_mrp, sbook_op, sbook_ctgry, sbook_desc)
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