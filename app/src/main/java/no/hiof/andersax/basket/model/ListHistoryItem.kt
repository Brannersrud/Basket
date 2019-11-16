package no.hiof.andersax.basket.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import no.hiof.andersax.basket.R
import java.util.*


class ListHistoryItem(val pricepaid : Long, val listName : String, var date : Date, var isPrivate : String) : Parcelable {

    constructor() : this(pricepaid = 0, listName= "", date = Date(), isPrivate = "")


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        Date(parcel.readLong()),
        parcel.readString()!!
    ) {
    }


    override fun writeToParcel(parcel : Parcel?, p1: Int) {
        parcel!!.writeLong(pricepaid)
        parcel!!.writeString(listName)
        parcel!!.writeLong(date.time)
        parcel!!.writeString(isPrivate)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListHistoryItem> {
        override fun createFromParcel(parcel: Parcel): ListHistoryItem {
            return ListHistoryItem(parcel)
        }

        override fun newArray(size: Int): Array<ListHistoryItem?> {
            return arrayOfNulls(size)
        }
    }


}