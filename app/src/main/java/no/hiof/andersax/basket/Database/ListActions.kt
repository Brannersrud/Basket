package no.hiof.andersax.basket.Database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter

class ListActions {
    private var Auth: AuthActions = AuthActions()


    fun addPrivateList(list: ListCollection, uid : String) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList").document(uid)
        ref.get()
            .addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    overWritePrivateList(uid, list)
                }else{
                    addNewPrivateList(list)
                }
            }
    }

    private fun addNewPrivateList(list: ListCollection) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")

        ref.add(list)
            .addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    //do some fancy stuff, toast for user
                }else{
                    //do some less fancy, negative toast
                }
            }

    }
    private fun overWritePrivateList(uid: String, list: ListCollection) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList").document(uid)
        ref.update("items", list.getListItems())
            .addOnCompleteListener {Task ->
                if(Task.isSuccessful){
                    //probably should show an update toast to the user
                }else{
                    //something went wrong ?
                }

            }
    }


/*
    fun getPrivateLists(presenter: ListPresenter) {
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
                presenter.setPrivateLists(lists)

            }
    }
    fun getListItems(uid : String, presenter: ListPresenter) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList").document(uid)
        var lists: List<ListItem> = ArrayList()
        ref.get()
            .addOnSuccessListener { it ->
                    var l: List<ListItem> = it.data!!.get("items") as List<ListItem>



                lists = l;


                presenter.setListItems(lists)
            }

    }

 */
}







/*
    fun setPrivateLists(lists : ArrayList<List>){
        presenter.setPrivateLists(lists)
    }

*/

