package com.booxapp.BookBuzz

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
const val API_KEY = "87d353ad02754239a6e1dbf63b1756a0"

interface ApiService {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getBookHeadlines(@Query("country") country: String, @Query("page") page: Int) : Call<News>
}

