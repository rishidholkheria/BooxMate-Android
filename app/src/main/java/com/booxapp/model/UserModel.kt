package com.booxapp.model

class UserModel {
    var id: String? = null
    var name: String? = null
    var loc: String? = null
    var phone: String? = null
    var email: String? = null

    constructor(id: String?, name: String?, loc: String?, phone: String?, email: String?) {
        this.id = id
        this.name = name
        this.loc = loc
        this.phone = phone
        this.email = email
    }

    constructor() {}
}