package no.hiof.andersax.basket.model

import no.hiof.andersax.basket.IuserList
import no.hiof.andersax.basket.presenter.AuthPresenter
import no.hiof.andersax.basket.presenter.UserPresenter

open class User(val phone : String, val email : String){
    val privateList = ArrayList<ListCollection>()
    val sharedList = ArrayList<ListCollection>()

    fun addSharedList(list: ListCollection?) {
        sharedList.add(list!!)
    }

    fun addPrivateList(list: ListCollection?) {
        privateList.add(list!!)
    }

}