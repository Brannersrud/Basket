package no.hiof.andersax.basket.presenter

import android.os.Handler
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.model.ListMembers
import no.hiof.andersax.basket.model.sharedList
import no.hiof.andersax.basket.view.createListFragment
import no.hiof.andersax.basket.view.listOverviewFragment
import java.util.ArrayList
import java.util.zip.ZipEntry

class ListPresenter{
    private var privateLists : List<ListCollection> = ArrayList()
    private var listactions : ListActions = ListActions()
    private var currentList : MutableList<ListItem> = ArrayList()
    private var Auth : AuthActions = AuthActions()
    private var currentUserName : String = ""
     fun addSharedList(listFragment: createListFragment, owner: String, listname: String, description: String,members : MutableList<ListMembers>, items: MutableList<ListItem>, totalprice: Long) {
         var sharedListToAdd :sharedList = sharedList(members, listname, description, owner, items, totalprice)
         listactions.addNewSharedList(sharedListToAdd, listFragment)
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
    fun getPrivateLists(fragment: listOverviewFragment) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")
        var lists: ArrayList<ListCollection> = ArrayList()
        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var l: MutableList<ListItem> = document.get("items")!! as MutableList<ListItem>
                    var owner = document.get("owner").toString()
                    var listname = document.get("listname").toString()
                    var description = document.get("description").toString()
                    var listcollection = ListCollection(listname, description, owner, l, 0)
                    listcollection.setUid(document.id)
                    lists.add(listcollection)
                    fragment.setUpListRecyclerView(lists);
                }
            }
    }

    fun getSharedLists(fragment: listOverviewFragment, uname : String){
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("sharedList")
        var list : ArrayList<sharedList> = ArrayList()

         ref.whereArrayContains("members", this.currentUserName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var l: MutableList<ListItem> = document.get("items")!! as MutableList<ListItem>
                    var members: MutableList<ListMembers> =
                        document.get("members") as MutableList<ListMembers>
                    var price: Long = document.get("totalPrice") as Long
                    var owner = document.get("owner").toString()
                    var listname = document.get("listname").toString()
                    var description = document.get("description").toString()
                    var listcollection = sharedList(members, listname, description, owner, l, price)
                    listcollection.setUid(document.id)
                    list.add(listcollection)
                }
            }

        ref.whereEqualTo("description", uname)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var l: MutableList<ListItem> = document.get("items")!! as MutableList<ListItem>
                    var members: MutableList<ListMembers> =
                        document.get("members") as MutableList<ListMembers>
                    var price: Long = document.get("totalPrice") as Long
                    var owner = document.get("owner").toString()
                    var listname = document.get("listname").toString()
                    var description = document.get("description").toString()
                    var listcollection = sharedList(members, listname, description, owner, l, price)
                    listcollection.setUid(document.id)
                    list.add(listcollection)

                    fragment.setUpSharedListRecyclerView(list)
                }

            }

    }


    fun getEmailOfUser(frag : listOverviewFragment) {
        val email = Auth.getCurrentUser().email
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("Users")
        Handler().postDelayed({
            getPrivateLists(frag)
        },300)

        ref.whereEqualTo("email", email)
            .get()
            .addOnCompleteListener {task ->
                if(task.isSuccessful)
                task.result!!
                    .asSequence()
                    .forEach {it ->
                       var username =  it.id
                        getSharedLists(frag, username)
                    }
            }

    }



}