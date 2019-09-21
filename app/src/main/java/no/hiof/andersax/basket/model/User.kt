package no.hiof.andersax.basket.model

import no.hiof.andersax.basket.IuserList
import no.hiof.andersax.basket.presenter.AuthPresenter
import no.hiof.andersax.basket.presenter.UserPresenter

class User(val phone : String, val email : String, val password : String) : IuserList {
    val privateList = ArrayList<List>()
    val sharedList = ArrayList<List>()

    override fun addSharedList(list: List?) {
        sharedList.add(list!!)
    }

    override fun addPrivateList(list: List?) {
        privateList.add(list!!)
    }
}