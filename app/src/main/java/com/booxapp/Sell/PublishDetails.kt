package com.booxapp.Sell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.booxapp.Constants
import com.booxapp.databinding.FragmentPublishDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PublishDetails : Fragment() {

    var publishBooksDB: DatabaseReference? = null

    lateinit var binding: FragmentPublishDetailsBinding
    private var bundle: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPublishDetailsBinding.inflate(inflater, container, false)
        publishBooksDB = FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)

//        val sbook_name = binding.title!!.getText().toString()
//        val sbook_desc = binding.description!!.getText().toString()
//        val sbook_ctgry = binding.category!!.getSelectedItem().toString()
//
//        bundle = Bundle()
//        bundle!!.putString("name", sbook_name)
//        bundle!!.putString("desc", sbook_desc)
//        bundle!!.putString("ctgry", sbook_ctgry)


        return binding.root
    }

}