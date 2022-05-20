package com.booxapp.store

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.booxapp.*
import com.booxapp.databinding.FragmentBookImagesBinding
import com.booxapp.model.BooxstoreModel
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class BooxStoreFragment : Fragment() {

    private var storageReference: StorageReference? = null

    lateinit var bookModel: BooxstoreModel
    lateinit var bookId: String
    lateinit var model: Bundle
    lateinit var binding: FragmentBookImagesBinding
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
        binding = FragmentBookImagesBinding.inflate(inflater, container, false)
        storageReference = FirebaseStorage.getInstance().reference

        val bundle = this.arguments
        bookModel = bundle!!.getParcelable("bookModel")!!
        model = bundle.getParcelable("bookBundle")!!

        Log.e("BSF", "emooty"+bookModel.toString())
        Log.e("BSF", "emooty"+model.toString())

        binding.confirmBookTitle.text = bookModel.bName
        binding.confirmBookCategory.text = bookModel.bContact
        binding.confirmSellerLocation.text = bookModel.bAddress

        bookModel.category = model.getString("ctgry")
        bookModel.description = model.getString("desc")
        bookModel.id = model.getString("bookid")
        bookModel.imagelink = model.getString("image")
        bookModel.mrp = model.getString("mrp")
        bookModel.offeredprice = model.getString("oprice")
        bookModel.status = model.getBoolean("status")
        bookModel.stockCount = model.getString("stockCount")
        bookModel.title = model.getString("booktitle")

        binding.confirmPost.setOnClickListener{
            FirebaseAdapter(requireActivity()).buyBookFromBooxStore(
                bookModel,
                object : onCompleteFirebase {
                    override fun onCallback(value: Boolean) {
                        if (value) {
                            shareData.passingData(2, null)
                            Toast.makeText(
                                requireActivity(),
                                "Book purchased ",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
        }

        return binding.root
    }


}