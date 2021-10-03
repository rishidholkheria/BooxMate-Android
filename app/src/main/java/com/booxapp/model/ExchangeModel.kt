package com.booxapp.model

import android.os.Parcel
import android.os.Parcelable

class ExchangeModel : Parcelable {
    var title: String? = null
    var location: String? = null
    var id: String? = null
    var category: String? = null
    var expectedBooks: String? = null
    var description: String? = null
    var imagelink: String? = null
    var bookmark: Boolean? = null
    var exchangeRequests: String? = null

    constructor() {}

    constructor(
        title: String?,
        location: String?,
        category: String?,
        expectedBooks: String?,
        description: String?,
        id: String?,
        imagelink: String?
    ) {
        this.title = title
        this.location = location
        this.category = category
        this.expectedBooks = expectedBooks
        this.description = description
        this.imagelink = imagelink
        this.id = id

    }

    constructor(
        title: String?,
        location: String?,
        category: String?,
        expectedBooks: String?,
        description: String?
    ) {
        this.title = title
        this.location = location
        this.category = category
        this.expectedBooks = expectedBooks
        this.description = description
    }

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        location = parcel.readString()
        id = parcel.readString()
        category = parcel.readString()
        expectedBooks = parcel.readString()
        description = parcel.readString()
        imagelink = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(location)
        parcel.writeString(category)
        parcel.writeString(expectedBooks)
        parcel.writeString(description)
        parcel.writeString(imagelink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExchangeModel> {
        override fun createFromParcel(parcel: Parcel): ExchangeModel {
            return ExchangeModel(parcel)
        }

        override fun newArray(size: Int): Array<ExchangeModel?> {
            return arrayOfNulls(size)
        }
    }


}



