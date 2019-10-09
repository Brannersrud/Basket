package no.hiof.andersax.basket.presenter

import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import java.util.ArrayList

class ListPresenter{
    private var privateLists : List<ListCollection> = ArrayList()
    private var listactions : ListActions = ListActions()
    private var currentList : MutableList<ListItem> = ArrayList()

     fun addSharedList(listname: String?, description: String?, userEmails: ArrayList<String>?, items: MutableList<ListItem>?, totalprice: Long) {
        TODO("Not implemented")
    }

    fun addPrivateList(
        listname: String,
        description: String,
        owner: String,
        totalprice: Long,
        id: String
    ) {
        val mynewlist = ListCollection(listname, description, owner,currentList, 0)
        listactions.addPrivateList(mynewlist, id)
    }


    fun addItemToList(item : ListItem){
        currentList.add(item)
    }

    fun setPrivateLists(list : ArrayList<ListCollection>){
        privateLists = list
        println(privateLists)

    }

    fun addCurrentList(list : MutableList<ListItem>){
        currentList.addAll(list)
    }
    fun removeListItem(index : Int){
        currentList.removeAt(index)
    }
    fun getCurrentList() : MutableList<ListItem>{
        return this.currentList
    }





}