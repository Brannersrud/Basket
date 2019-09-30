package no.hiof.andersax.basket.presenter

import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.IuserList
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.model.sharedList
import java.util.ArrayList

class ListPresenter : IuserList{
    private val listactions : ListActions = ListActions()
    private var privateLists : ArrayList<ListCollection> = ArrayList()

    override fun addSharedList(listname: String?, description: String?, userEmails: ArrayList<String>?, items: List<ListItem>?) {
        TODO("Not implemented")
    }

    override fun addPrivateList(listname: String, description: String, owner: String, items: List<ListItem>) {
            val mynewlist = ListCollection(listname, description, owner,items)
            listactions.addPrivateList(mynewlist)
        }

    override fun AddListItems(listId: Int, item: MutableList<ListItem>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun UpdateSharedList(listId: Int, list: MutableList<ListItem>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateSingleList(listId: Int, list: MutableList<ListItem>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun CheckListItem(item: ListItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setPrivateLists(list : ArrayList<ListCollection>){
        privateLists = list

    }
    fun getPrivateLists() : ArrayList<ListCollection>{
        return privateLists
    }


}