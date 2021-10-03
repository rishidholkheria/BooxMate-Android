package com.booxapp

import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel

interface ShareData {
    fun passingData(choice: Int, bookModel1: BookModel?);
}

interface ExShareData {
    fun passingData(choice: Int, exBookModel1: ExchangeModel?);
}