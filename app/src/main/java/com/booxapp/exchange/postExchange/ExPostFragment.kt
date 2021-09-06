package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.adapter.SellAdapter
import com.booxapp.databinding.FragmentExchangeBinding
import com.booxapp.databinding.FragmentSellBinding
import com.booxapp.model.BookModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*

class ExPostFragment : Fragment() {

    lateinit var binding: FragmentExchangeBinding

    private val TAG = "ExchangeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExchangeBinding.inflate(inflater, container, false)
        

        return binding.root
    }

}