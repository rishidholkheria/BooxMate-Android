package com.booxapp.model

class UserModel {
    var id: String? = null
    var name: String? = null
    var loc: String? = null
    var phone: String? = null
    var email: String? = null
    var password: String? = null
    var bookmarkedBooks: String? = null

    constructor(
        bookmarkedBooks: String?
    ) {
        this.bookmarkedBooks = bookmarkedBooks
    }

    constructor() {}
}