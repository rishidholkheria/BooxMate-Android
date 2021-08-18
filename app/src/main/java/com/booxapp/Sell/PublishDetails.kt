package com.booxapp.Sell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booxapp.R
import com.booxapp.databinding.FragmentPublishDetailsBinding

class PublishDetails : Fragment() {

    lateinit var binding: FragmentPublishDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish_details, container, false)
    }

}