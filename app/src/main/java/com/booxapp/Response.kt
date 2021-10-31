package com.booxapp

import com.booxapp.model.BookModel

data class Response(
    var books: List<BookModel>? = null,
    var exception: Exception? = null
)
