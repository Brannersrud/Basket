package no.hiof.andersax.basket.presenter

import android.os.Handler
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.model.ListMembers
import no.hiof.andersax.basket.model.sharedList
import no.hiof.andersax.basket.profileActivity
import no.hiof.andersax.basket.view.createListFragment
import no.hiof.andersax.basket.view.listOverviewFragment
import no.hiof.andersax.basket.view.privateListFragment
import no.hiof.andersax.basket.view.sharedListFragment
import java.util.ArrayList

class ListPresenter{
    private var listactions : ListActions = ListActions()
    private var currentList : MutableList<ListItem> = ArrayList()
    private var currentSharedList : MutableList<ListItem> = ArrayList()
    private var Auth : AuthActions = AuthActions()

    //MAKE ONE FOR SHARED, ONE FOR PRIVATE AND INTERFACE THAT BOTH CAN GET?
     fun addSharedList(listFragment: sharedListFragment, owner: String, listname: String, description: String,members : MutableList<ListMembers>, items: MutableList<ListItem>, totalprice: Long, uid : String) {
        val usernames : MutableList<String> = ArrayList()
         for (member in members){
             usernames.add(member.username)
         }

         var sharedListToAdd : sharedList = sharedList(members, usernames,listname,description,owner,items,calculateTotalPrice(items))
         listactions.addSharedList(sharedListToAdd, listFragment)
    }

    fun updateSharedList(listFragment: sharedListFragment, uid : String){
        listactions.overWriteSharedList(uid, currentSharedList, listFragment, calculateTotalPrice(currentSharedList))
        //need to update the price 2 stupid
    }

    fun addPrivateList(listname: String, description: String, owner: String, totalprice: Long, id: String, fragment : privateListFragment) {
        val mynewlist = ListCollection(listname, description, owner,currentList, calculateTotalPrice(currentList))
        listactions.addPrivateList(mynewlist, id, fragment)
    }

      fun calculateTotalPrice(list: MutableList<ListItem>): Long{
        var temp: Long = 0;
        for (i in list) {
            if (i.isChecked) {
                temp += i.price;
            }
        }
        return temp
    }

    fun addItemToList(item : ListItem, fragment : privateListFragment){
        currentList.add(item)
        fragment.setUpSingleListRecyclerView()
    }

    fun addItemToSharedList(item : ListItem, fragment: sharedListFragment){
        currentSharedList.add(item)
        fragment.setUpSharedRecyclerView(currentSharedList)
    }
    fun addCurrentSharedList(list:MutableList<ListItem>){
        currentSharedList.addAll(list)
    }

    fun addCurrentList(list : MutableList<ListItem>){
        currentList.addAll(list)
    }

    fun getCurrentSharedList():MutableList<ListItem>{
        return this.currentSharedList
    }
    fun getCurrentList() : MutableList<ListItem>{
        return this.currentList
    }
    fun getPrivateLists(fragment: listOverviewFragment) {
        val db = Auth.getFireBaseStoreReference()
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
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("sharedList")
        var list : ArrayList<sharedList> = ArrayList()
        var memberUsernames : MutableList<String> = ArrayList<String>()


        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
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
                    var listcollection = sharedList(members, memberUsernames,listname,description,owner,l,price)
                    listcollection.setUid(document.id)
                    list.add(listcollection)

                }
            }
        ref.whereArrayContains("usernames", uname.toString())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var l: MutableList<ListItem> = document.get("items")!! as MutableList<ListItem>
                    var members: MutableList<ListMembers> =
                        document.get("members")!! as MutableList<ListMembers>
                    //var memberUsernames = document.get("memberUsernames") as MutableList<String>
                    var price: Long = document.get("totalPrice") as Long
                    var owner = document.get("owner").toString()
                    var listname = document.get("listname").toString()
                    var description = document.get("description").toString()
                    var listcollection = sharedList(members, memberUsernames,listname,description,owner,l,price)
                    listcollection.setUid(document.id)
                    list.add(listcollection)
                }
            }
        fragment.setUpSharedListRecyclerView(list)
    }


    fun getListOverViews(frag : listOverviewFragment) {
        val email = Auth.getCurrentUser().email
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("Users")
        Handler().postDelayed({
            getPrivateLists(frag)
        },300)
        var uname = ""
        ref.whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result!!
                        .asSequence()
                        .forEach { it ->
                            uname = it.id

                        }
                    getSharedLists(frag, uname)


                }
            }
    }


    fun getProfileInformation(activity : profileActivity) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("sharedList")
        var refPriv = db.collection("sharedList")
        var usersref = db.collection("Users")
        var sharedres = 0
        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sharedres = task.result!!.size()
                    activity.setUpPrivateCount(sharedres)
                }
            }
        var privRes = 0;
        refPriv.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    privRes = task.result!!.size()
                    activity.setUpPrivateCount(privRes)
                }
            }

        var name = "";
        usersref.whereEqualTo("email", Auth.getCurrentUser().email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result!!
                        .asSequence()
                        .forEach { it ->
                            name = it.id

                        }
                }
                activity.setUpName(name)
            }

    }


    fun getHistoryOfPayments(){
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("History")
        //need to implement the paymenthistory

    }



}