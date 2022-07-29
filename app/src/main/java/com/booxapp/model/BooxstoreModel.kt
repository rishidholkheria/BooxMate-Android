package com.booxapp.model

import android.os.Parcel
import android.os.Parcelable

class BooxstoreModel: Parcelable {
    var id: String? = null
    var title: String? = null
    var bName: String? = null
    var mrp: String? = null
    var offeredprice: String? = null
    var category: String? = null
    var description: String? = null
    var imagelink: String? = null
    var bAddress: String? = null
    var bContact: String? = null
    var buyerId: String? = null
    var stockCount: String? = null
    var status: Boolean? = true

    constructor() {}

    //basic constructor

    constructor(
        id: String?,
        title: String?,
        mrp: String?,
        offeredprice: String?,
        category: String?,
        description: String?,
        imagelink: String?,
        buyerId: String?,
        bAddress: String?,
        bContact: String?,
        stockCount: String?,
        status: Boolean?
    ) {
        this.id = id
        this.title = title
        this.mrp = mrp
        this.offeredprice = offeredprice
        this.category = category
        this.description = description
        this.imagelink = imagelink
        this.buyerId = buyerId
        this.bAddress = bAddress
        this.bContact= bContact
        this.stockCount = stockCount
        this.status = status
    }

    constructor(
        bName: String?,
        bAddress: String?,
        bContact: String?
    )
    {
        this.bName = bName
        this.bAddress = bAddress
        this.bContact = bContact
    }


    //Used in Passing of data in Fragments(SellDetails)
    constructor(parcel: Parcel) : this() {
        bName = parcel.readString()
        bAddress = parcel.readString()
        bContact = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bName)
        parcel.writeString(bAddress)
        parcel.writeString(bContact)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BooxstoreModel> {
        override fun createFromParcel(parcel: Parcel): BooxstoreModel {
            return BooxstoreModel(parcel)
        }

        override fun newArray(size: Int): Array<BooxstoreModel?> {
            return arrayOfNulls(size)
        }
    }

}

