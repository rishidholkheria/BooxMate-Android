package com.booxapp.model

import com.booxapp.purchase.BookmarkedBooks

class ViewRequestsModel {
    var buyerId: String? = null
    var message: String? = null
    var buyerName: String? = null
    var buyerLoc: String? = null
    var buyerNumber: String? = null

    constructor() {}

    constructor(buyerName: String, buyerLoc: String, buyerNumber: String){
        this.buyerName = buyerName
        this.buyerLoc = buyerLoc
        this.message = buyerNumber
    }

}