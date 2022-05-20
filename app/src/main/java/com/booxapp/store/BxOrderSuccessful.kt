package com.booxapp.store

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booxapp.BxShareData
import com.booxapp.ShareData
import com.booxapp.databinding.ActivityConfirmPostBinding

class BxOrderSuccessful : Fragment() {
    lateinit var binding: ActivityConfirmPostBinding
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
        binding = ActivityConfirmPostBinding.inflate(inflater, container, false)


        return binding.root
    }
}