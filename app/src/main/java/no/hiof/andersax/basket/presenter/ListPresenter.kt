package no.hiof.andersax.basket.presenter

import android.os.Handler
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.IuserList
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.model.sharedList
import java.lang.NullPointerException
import java.util.ArrayList

class ListPresenter : IuserList{
    private var privateLists : List<ListCollection> = ArrayList()
    private var listItems : ArrayList<ListItem> = ArrayList()
    private var listactions : ListActions = ListActions()
    private var Auth : AuthActions = AuthActions()

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
    fun getSinglePrivateList(uid : String){
        var list : List<ListItem> = ArrayList()
        privateLists.forEach {
            Log.d("this", it.listname)
        }
    }

    fun setPrivateLists(list : ArrayList<ListCollection>){
        privateLists = list
        println(privateLists)

    }
   /* fun getPrivateLists() : ArrayList<ListCollection>{
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")
        var lists: ArrayList<ListCollection> = ArrayList()
        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var l: List<ListItem> = document.get("items")!! as List<ListItem>
                    var owner = document.get("owner").toString()
                    var listname = document.get("listname").toString()
                    var description = document.get("description").toString()
                    var listcollection = ListCollection(listname, description, owner, l)
                    listcollection.setUid(document.id)

                    lists.add(listcollection)

                }
                Log.d("list from presenter", lists.toString())

            }

        return lists;

    }
*/

    fun setListItems(items : List<ListItem>){
        listItems.addAll(items)
    }




}