package no.hiof.andersax.basket.presenter

import android.util.Log
import no.hiof.andersax.basket.model.User

class UserPresenter {
     val userlist : MutableList<String> = ArrayList<String>()
    //getters and setters to useractions
    //search for users

     fun addUsersToList(list : MutableList<String>){
        userlist.addAll(list)

         Log.d("Users have been fetched", this.userlist.toString())
    }

     fun getAllUsers() : MutableList<String> {
        return this.userlist
    }





}