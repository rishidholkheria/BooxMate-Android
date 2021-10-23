package com.booxapp.BookReviews

import com.booxapp.BookBuzz.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "Gyv6iYLxV3sbZkkGQSglvGyiukGDiUQ9"


interface ReviewApiService {
    @GET("svc/books/v3/reviews.json?&api-key=$API_KEY")
    fun getReviews(@Query("author") country: String, @Query("page") page: Int): Call<News>
}