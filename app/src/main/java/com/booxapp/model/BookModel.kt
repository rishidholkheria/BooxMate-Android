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
    var seller_name: String? = null
    var seller_email: String? = null
    var imagelink: String? = null

    constructor() {}

    //BasicModel
    constructor(
        title: String?,
        location: String?,
        mrp: String?,
        offeredprice: String?,
        id: String?,
        category: String?,
        bookmark: Boolean?,
        description: String?,
        seller_name: String?,
        seller_email: String?,
        imagelink: String?
    ) {
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
        seller_name = parcel.readString()
        seller_email = parcel.readString()
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
        parcel.writeString(seller_name)
        parcel.writeString(seller_email)
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
}