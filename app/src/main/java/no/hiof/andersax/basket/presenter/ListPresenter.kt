package no.hiof.andersax.basket.presenter

import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.IuserList
import no.hiof.andersax.basket.model.List
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.model.sharedList
import java.util.ArrayList

class ListPresenter : IuserList{
    private val listactions : ListActions = ListActions()

    override fun addSharedList(uid: String?, listname: String?, description: String?, userEmails: ArrayList<String>?, items: ArrayList<ListItem>?) {
        TODO("Not implemented")
    }

    override fun addPrivateList(uid: String?, listname : String, description : String, items : ArrayList<ListItem>) {
        val emptyItems = ArrayList<ListItem>()
        emptyItems.add(ListItem("Banana", 2, false, 0))
       val newlist = List(listname, description, "You", emptyItems)
        listactions.addPrivateList(newlist)
    }

    override fun AddListItems(listId: Int, item: ArrayList<ListItem>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun UpdateSharedList(listId: Int, list: ArrayList<ListItem>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateSingleList(listId: Int, list: ArrayList<ListItem>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun CheckListItem(item: ListItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //getlists from userid.
}