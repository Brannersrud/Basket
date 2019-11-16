package no.hiof.andersax.basket.model

import android.os.Parcel
import android.os.Parcelable

data class ListHistoryCollection(var ListHistoryItems : Array<ListHistoryItem>) : Parcelable{


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedArray(ListHistoryItems, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListHistoryCollection> {
        override fun createFromParcel(parcel: Parcel) = ListHistoryCollection(parcel.createTypedArray(ListHistoryItem.CREATOR) as Array<ListHistoryItem>)


        override fun newArray(size: Int): Array<ListHistoryCollection?> {
            return arrayOfNulls(size)
        }
    }

}