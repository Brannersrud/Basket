package no.hiof.andersax.basket.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

class ListMembers(var username: String, var hasPaid : Boolean, var checked : Boolean, var amountpaid : Long){

constructor() : this(username="", hasPaid = false, checked = false, amountpaid = 0)
}






