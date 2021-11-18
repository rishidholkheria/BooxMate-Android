package com.booxapp.BookBuzz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.booxapp.databinding.ActivityBookBuzzBinding
import com.booxapp.databinding.BookBuzzWebViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookBuzzWebview : AppCompatActivity() {
    private lateinit var binding: BookBuzzWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BookBuzzWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val news_link = intent.getStringExtra("newsLink")

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.loadUrl(news_link.toString())

    }

}