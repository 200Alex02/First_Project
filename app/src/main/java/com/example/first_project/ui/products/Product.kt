package com.example.first_project.ui.products

import android.os.Parcel
import android.os.Parcelable
import com.example.first_project.ui.database.ProductEntity

data class Product(
    val brand: String,
    val description: String,
    val picture: String,
    val cost: String,
    var likeElement: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(brand)
        parcel.writeString(description)
        parcel.writeString(picture)
        parcel.writeString(cost)
        parcel.writeByte(if (likeElement) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
