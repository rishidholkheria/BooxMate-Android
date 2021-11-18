package com.booxapp.BookBuzz

import android.R
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.booxapp.databinding.ActivityBookBuzzBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookBuzz : AppCompatActivity() {
    private lateinit var binding: ActivityBookBuzzBinding

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<String>()
    private var urlList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookBuzzBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toobar)

        binding.toolbarLayout.toobar.overflowIcon?.setTint(Color.WHITE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //For back btn
        supportActionBar?.setDisplayShowHomeEnabled(true) //Both lines for back btn

        bookBuzzCall()

    }

    //for back btn toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bookBuzzCall(){
        val bookBuzzService = BookBuzzService.buildService(ApiService::class.java)
        val call = bookBuzzService.getBookHeadlines("books, new authors, new books",1)
        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    val news: News? = response.body()
                    Log.e("BOOOOOK", response.body().toString())
                    for (article in news!!.articles) {
                        addToList(article.title, article.description, article.urlToImage, article.url)
                    }

                    binding.bookBuzzRv.apply {
                        layoutManager = LinearLayoutManager(this@BookBuzz)
                        adapter = BookBuzzAdapter(titlesList, descList, imagesList, urlList)
                    }
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Bookbuzz", "Error in Fetching Book Buzz", t)
            }
        })
    }

    private fun addToList(title: String, description: String, urlToImage: String, url: String) {
        titlesList.add(title)
        descList.add(description)
        imagesList.add(urlToImage)
        urlList.add(url)
    }

}