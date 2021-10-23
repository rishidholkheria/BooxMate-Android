package com.booxapp.model

class UserModel {
    var id: String? = null
    var userId: String? = null
    var name: String? = null
    var loc: String? = null
    var phone: String? = null
    var email: String? = null
    var password: String? = null
    var bookmarkedBooks: String? = null
    var requestedBook: String? = null

    constructor() {}

    constructor(userId: String?, requestedBook: String?) {
        this.userId = userId
        this.requestedBook = requestedBook
    }


}