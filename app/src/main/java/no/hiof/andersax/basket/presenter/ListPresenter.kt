package no.hiof.andersax.basket.presenter

import android.os.Handler
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.Helpers.ListCalculator
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
    private var listcalculator : ListCalculator = ListCalculator()
    private var store : FirebaseFirestore  = FirebaseFirestore.getInstance()



    fun addItemToList(item : ListItem, fragment : privateListFragment){
        currentList.add(item)
        fragment.setUpSingleListRecyclerView()
    }

    fun addItemToSharedList(item : ListItem, fragment: sharedListFragment){
        currentSharedList.add(item)
        fragment.setUpSharedRecyclerView()
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
     fun addSharedList(listFragment: createListFragment, owner: String, listname: String, description: String,members : MutableList<ListMembers>, items: MutableList<ListItem>) {
        val usernames : MutableList<String> = ArrayList()
         for (member in members){
             usernames.add(member.username)
         }
         val sharedListToAdd = sharedList(members, usernames,listname,description,owner,items,listcalculator.calculateTotal(items), ArrayList<ListItem>())
         listactions.addSharedList(sharedListToAdd, listFragment)
    }


    fun addPrivateList(listname: String, description: String, owner: String, id: String, fragment : privateListFragment) {
        val mynewlist = ListCollection(listname, description, owner,currentList,  listcalculator.calculateTotal(currentList), ArrayList<ListItem>())
        listactions.addPrivateList(mynewlist, id, fragment)
    }

    private fun getPrivateLists(fragment: listOverviewFragment) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("privateList")
        val lists: ArrayList<ListCollection> = ArrayList()

        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val obj = document.toObject(ListCollection::class.java)
                    obj.setUid(document.id)
                    lists.add(obj)
                }
                fragment.setUpListRecyclerView(lists)

            }.addOnFailureListener { e ->
                e.suppressed
            }
    }

    //get shared lists
    private fun getSharedLists(fragment: listOverviewFragment, uname : String){
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("sharedList")
        val list : ArrayList<sharedList> = ArrayList()
        //the ones you own
            ref.whereEqualTo("owner", Auth.getCurrentUser().email)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val obj = document.toObject(sharedList::class.java)
                        obj.setUid(document.id)
                        list.add(obj)
                    }
                    fragment.setUpSharedListRecyclerView(list)
                }
        //where you are a member
            ref.whereArrayContains("usernames", uname)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val obj = document.toObject(sharedList::class.java)

                        obj.setUid(document.id)
                        list.add(obj)

                    }
                    fragment.setUpSharedListRecyclerView(list)

                }
    }


    //get both kinds of lists
    fun getListOverViews(frag : listOverviewFragment) {
        val email = Auth.getCurrentUser().email
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("Users")
        Handler().postDelayed({
            getPrivateLists(frag)
        },800)
        var uname = ""
        //fetching the username
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





    // DELETE AND UPDATE

    fun handleListDelete(isShared : Boolean, uid : String, fragment : listOverviewFragment){
        val db = Auth.getFireBaseStoreReference()
        val ref: String
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
            listactions.overWriteSharedList(uid, currentSharedList, listFragment, listcalculator.calculateTotal(currentSharedList), false)
        }else{
            listactions.overWriteSharedList(uid, currentSharedList, listFragment, listcalculator.calculateTotal(currentSharedList), true)

        }
    }

    //get listitems

     fun getListItems(id: String, fragment : Fragment, private : Boolean, collectionName : String) {
        val ref = store.collection(collectionName)
        ref.get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    task.result!!
                        .asSequence()
                        .filter { it.id == id }
                        .forEach {
                            val data : List<HashMap<String,Any>> = it.data["items"] as List<HashMap<String, Any>>

                            prepareData(data, fragment, private)
                        }
                }else{
                    error("Should i have an error field instead of posts?")
                }
            }
    }

    private fun prepareData(list: List<HashMap<String, Any>>, fragment : Fragment, private : Boolean) {
        val items : MutableList<ListItem> = ArrayList()
        var i = 0
        for(item in list){
            val p = list.get(i)["price"] as Long
            val n = list.get(i)["itemName"] as String
            val c = list.get(i)["checked"] as Boolean
            val q = list.get(i)["quantity"] as Long
            items.add(ListItem(n,q,c,p))
            i++

        }
        if(private) {
            addCurrentList(items)
            val privatefrag: privateListFragment = fragment as privateListFragment
            privatefrag.setUpSingleListRecyclerView()
        }else{
            addCurrentSharedList(items)
            val sharedFrag : sharedListFragment = fragment as sharedListFragment

            sharedFrag.setUpSharedRecyclerView()
        }
    }


}