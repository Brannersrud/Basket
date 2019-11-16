package no.hiof.andersax.basket.presenter

import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
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
import java.lang.reflect.GenericArrayType
import java.net.ConnectException
import java.util.ArrayList

class ListPresenter{
    private var listactions : ListActions = ListActions()
    private var currentList : MutableList<ListItem> = ArrayList()
    private var currentSharedList : MutableList<ListItem> = ArrayList()
    private var Auth : AuthActions = AuthActions()


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

    //MAKE ONE FOR SHARED, ONE FOR PRIVATE AND INTERFACE THAT BOTH CAN GET?
     fun addSharedList(listFragment: createListFragment, owner: String, listname: String, description: String,members : MutableList<ListMembers>, items: MutableList<ListItem>, totalprice: Long, uid : String) {
        val usernames : MutableList<String> = ArrayList()
         for (member in members){
             usernames.add(member.username)
         }

         var sharedListToAdd : sharedList = sharedList(members, usernames,listname,description,owner,items,calculateTotalPrice(items))
         listactions.addSharedList(sharedListToAdd, listFragment)
    }



    fun addPrivateList(listname: String, description: String, owner: String, totalprice: Long, id: String, fragment : privateListFragment) {
        val mynewlist = ListCollection(listname, description, owner,currentList, calculateTotalPrice(currentList))
        listactions.addPrivateList(mynewlist, id, fragment)
    }

    //calculate
      fun calculateTotalPrice(list: MutableList<ListItem>): Long{
        var temp: Long = 0;
        for (i in list) {
            if (i.isChecked) {
                temp += i.price;
            }
        }
        return temp
    }

    //get private
    fun getPrivateLists(fragment: listOverviewFragment) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("privateList")
        var lists: ArrayList<ListCollection> = ArrayList()

        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val obj = document.toObject(ListCollection::class.java)
                    obj.setUid(document.id)
                    lists.add(obj)
                }
                fragment.setUpListRecyclerView(lists);

            }.addOnFailureListener { e ->
                e.suppressed
            }
    }

    //get shared lists
    fun getSharedLists(fragment: listOverviewFragment, uname : String){
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("sharedList")
        var list : ArrayList<sharedList> = ArrayList()


        try {
            ref.whereEqualTo("owner", Auth.getCurrentUser().email)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val obj = document.toObject(sharedList::class.java)
                        obj.setUid(document.id)
                        list.add(obj)

                    }
                }
            ref.whereArrayContains("usernames", uname.toString())
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val obj = document.toObject(sharedList::class.java)

                        obj.setUid(document.id)
                        list.add(obj)
                    }
                    fragment.setUpSharedListRecyclerView(list)
                }


        }catch (e : ConnectException){
            //show toast
        }catch (e : Exception){
            println("some thing wong")
        }
    }


    //get det lists
    fun getListOverViews(frag : listOverviewFragment) {
        val email = Auth.getCurrentUser().email
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("Users")
        Handler().postDelayed({
            getPrivateLists(frag)
        },800)
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
            }.addOnFailureListener { e ->
                e.suppressed
            }
    }


    //fetch profile information and set it up
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
                    activity.setUpSharedCount(sharedres)
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
            }.addOnFailureListener { e ->
                e.suppressed
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
            }.addOnFailureListener {e ->
                e.suppressed
            }

    }


    // DELETE AND UPDATE

    fun handleListDelete(isShared : Boolean, uid : String, fragment : listOverviewFragment){
        var db = Auth.getFireBaseStoreReference()
        var ref: String
        if(isShared){
            ref = "sharedList"
        }else{
            ref ="privateList"
        }
        db.collection(ref).document(uid).delete().addOnCompleteListener { it ->
            if(it.isSuccessful){
                fragment.showToastToUser("Successfully deleted this old ragged list")
            }else{
                fragment.showToastToUser("this list really wont go away")
            }

        }.addOnFailureListener { e ->
            e.suppressed
        }

    }


    fun updateSharedList(listFragment: sharedListFragment, uid : String, updatefashion : String){
        if(updatefashion.equals("destroy")){
            listactions.overWriteSharedList(uid, currentSharedList, listFragment, calculateTotalPrice(currentSharedList), false)
            //need to update the price 2 stupid
        }else{
            listactions.overWriteSharedList(uid, currentSharedList, listFragment, calculateTotalPrice(currentSharedList), true)

        }
    }


}