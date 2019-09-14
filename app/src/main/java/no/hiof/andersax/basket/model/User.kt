package no.hiof.andersax.basket.model

import no.hiof.andersax.basket.IuserList
import no.hiof.andersax.basket.presenter.AuthPresenter
import no.hiof.andersax.basket.presenter.UserPresenter

abstract class User(val phone : String, val email : String, val password : String, val privateList : ArrayList<List>, val sharedList : ArrayList<List>) : IuserList {
    abstract val userPresenter : UserPresenter
    abstract val authPresenter : AuthPresenter


    override fun addSharedList(list: List?) {
        sharedList.add(list!!)
    }

    override fun addPrivateList(list: List?) {
        privateList.add(list!!)
    }
}