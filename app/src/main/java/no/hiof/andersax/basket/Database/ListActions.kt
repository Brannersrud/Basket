package no.hiof.andersax.basket.Database

import no.hiof.andersax.basket.IuserList
import no.hiof.andersax.basket.model.List

class ListActions : IuserList{


    override fun addSharedList(list: List?) {
        //add it to owner with members
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addPrivateList(list: List?) {
        //add it to owner without members
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //addPrivateListToDb(name, description, owner, Array of items)
    //getListFromDb(userID,listID) -> object of type list
    //getAllPrivateLists(userID) -> Array

    //addSharedListToDb(List, array of emails)
    //addMemberToList(email, list)
    //getAllSharedLists(userID) -> Array

    //removeItemFromList

}