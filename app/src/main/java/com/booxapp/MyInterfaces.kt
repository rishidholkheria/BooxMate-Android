package com.booxapp

import com.booxapp.model.BookModel
import com.booxapp.model.BooxstoreModel
import com.booxapp.model.ExchangeModel

interface ShareData {
    fun passingData(choice: Int, bookModel1: BookModel?);
}

interface BxShareData {
    fun passingData(choice: Int, bxBookModel1: BooxstoreModel?);
}

interface ExShareData {
    fun passingData(choice: Int, exBookModel1: ExchangeModel?);
}