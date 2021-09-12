package com.booxapp.model

class ExchangeModel {
    var title: String? = null
    var location: String? = null
    var id: String? = null
    var category: String? = null
    var expectedBooks: String? = null
    var description: String? = null
    var seller_name: String? = null
    var seller_email: String? = null
    var imagelink: String? = null
    var bookmark: Boolean? = null
    var exchangeRequests: String? = null

    constructor() {}

    constructor(
        title: String?,
        location: String?,
        id: String?,
        category: String?,
        expectedBooks: String?,
        description: String?,
        seller_name: String?,
        seller_email: String?,
        imagelink: String?,
        bookmark: Boolean?,
        exchangeRequests: String?
    ) {
        this.title = title
        this.location = location
        this.id = id
        this.category = category
        this.expectedBooks = expectedBooks
        this.description = description
        this.seller_name = seller_name
        this.seller_email = seller_email
        this.imagelink = imagelink
        this.bookmark = bookmark
        this.exchangeRequests = exchangeRequests
    }


}



