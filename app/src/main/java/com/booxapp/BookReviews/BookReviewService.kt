package com.booxapp.BookReviews

import com.booxapp.BookBuzz.BASE_URl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URl = "https://api.nytimes.com/"

object BookReviewService{
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    fun <T> buildService(service: Class<T>) : T{
        return retrofit.create(service)
    }
}