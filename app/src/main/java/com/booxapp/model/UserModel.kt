package com.booxapp.model

class UserModel {
    var id: String? = null
    var userId: String? = null
    var name: String? = null
    var loc: String? = null
    var phone: String? = null
    var email: String? = null
    var password: String? = null
    var bookmarkedBooks: ArrayList<String>? = null
    var requestedBook: String? = null

    constructor() {}

    constructor(userId: String?) {
        this.userId = userId
//        this.requestedBook = requestedBook
    }

    constructor(name: String?, loc: String?, phone: String?,id: String?) {
        this.name = name
        this.loc = loc
        this.phone = phone
        this.id = id
    }

    constructor(bookmarkedBooks: ArrayList<String>){
        this.bookmarkedBooks = bookmarkedBooks
    }

}