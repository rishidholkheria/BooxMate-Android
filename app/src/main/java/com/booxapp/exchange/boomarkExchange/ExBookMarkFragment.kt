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
import com.booxapp.databinding.FragmentExBookmarkBinding
import com.booxapp.databinding.FragmentExPostBinding
import com.booxapp.databinding.FragmentExchangeBinding
import com.booxapp.databinding.FragmentSellBinding
import com.booxapp.model.BookModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*

class ExBookMarkFragment : Fragment() {

    lateinit var binding: FragmentExBookmarkBinding

    private val TAG = "FragmentExPostBinding"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExBookmarkBinding.inflate(inflater, container, false)
        

        return binding.root
    }

}