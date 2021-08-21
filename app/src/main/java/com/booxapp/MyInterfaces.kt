package com.booxapp

import com.booxapp.model.BookModel

interface ShareData {
    fun passingData(choice: Int, bookModel1: BookModel);
}