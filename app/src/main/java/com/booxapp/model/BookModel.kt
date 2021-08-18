package com.booxapp.model

class BookModel {
    var title: String? = null
    var location: String? = null
    var mrp: String? = null
    var offeredprice: String? = null
    var id: String? = null
    var category: String? = null
    var bookmark: Boolean? = null
    var description: String? = null
    var seller_name: String? = null
    var seller_email: String? = null
    var imagelink: String? = null

    constructor(title: String?, location: String?, mrp: String?, offeredprice: String?, id: String?, category: String?, bookmark: Boolean?, description: String?, seller_name: String?, seller_email: String?, imagelink: String?) {
        this.title = title
        this.location = location
        this.mrp = mrp
        this.offeredprice = offeredprice
        this.id = id
        this.category = category
        this.bookmark = bookmark
        this.description = description
        this.seller_name = seller_name
        this.seller_email = seller_email
        this.imagelink = imagelink
    }


    constructor() {}
    constructor(title: String?, offeredprice: String?, imagelink: String?) {
        this.title = title
        this.offeredprice = offeredprice
        this.imagelink = imagelink
    }
}