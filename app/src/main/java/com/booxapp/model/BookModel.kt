package com.booxapp.model

import android.os.Parcel
import android.os.Parcelable

class BookModel : Parcelable {
    var title: String? = null
    var location: String? = null
    var mrp: String? = null
    var offeredprice: String? = null
    var id: String? = null
    var category: String? = null
    var bookmark: Boolean? = null
    var description: String? = null
    var imagelink: String? = null
    var userId: String? = null
    var requests: ArrayList<String> = ArrayList()

    constructor() {}

    //BasicModel
    constructor(
        title: String?,
        location: String?,
        mrp: String?,
        id: String?,
        offeredprice: String?,
        category: String?,
        bookmark: Boolean?,
        description: String?,
        imagelink: String?,
        userId: String?
    ) {
        this.title = title
        this.location = location
        this.mrp = mrp
        this.id = id
        this.offeredprice = offeredprice
        this.category = category
        this.bookmark = bookmark
        this.description = description
        this.imagelink = imagelink
        this.userId = userId
    }

    //PublishDataFragment
    constructor(
        title: String?,
        location: String?,
        mrp: String?,
        offeredprice: String?,
        category: String?,
        description: String?
    ) {
        this.title = title
        this.location = location
        this.mrp = mrp
        this.offeredprice = offeredprice
        this.category = category
        this.description = description
    }

    //Used in Passing of data in Fragments(SellDetails)
    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        location = parcel.readString()
        mrp = parcel.readString()
        offeredprice = parcel.readString()
        id = parcel.readString()
        category = parcel.readString()
        bookmark = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        description = parcel.readString()
        imagelink = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(location)
        parcel.writeString(mrp)
        parcel.writeString(offeredprice)
        parcel.writeString(id)
        parcel.writeString(category)
        parcel.writeValue(bookmark)
        parcel.writeString(description)
        parcel.writeString(imagelink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookModel> {
        override fun createFromParcel(parcel: Parcel): BookModel {
            return BookModel(parcel)
        }

        override fun newArray(size: Int): Array<BookModel?> {
            return arrayOfNulls(size)
        }
    }


    constructor(userId: String?) {
        this.userId = userId
//        this.requestedBook = requestedBook
    }

}