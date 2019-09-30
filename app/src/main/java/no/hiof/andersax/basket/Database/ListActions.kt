package no.hiof.andersax.basket.Database

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter

class ListActions {
    private var Auth : AuthActions = AuthActions()



    fun addPrivateList(list : ListCollection){
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")
        ref.add(list)
            .addOnSuccessListener {
                Log.d("successfull", "jyeee")
            }
    }

    fun getPrivateLists(presenter: ListPresenter){
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")
        var lists : ArrayList<ListCollection> = ArrayList()
        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    var l : List<ListItem> = document.get("items") as List<ListItem>
                    var owner = document.get("owner").toString()
                    var listname = document.get("listname").toString()
                    var description = document.get("description").toString()

                    var listcollection = ListCollection(listname,description, owner, l)

                    lists.add(listcollection)
                }
                presenter.setPrivateLists(lists)

            }

    }
}

/*
    fun setPrivateLists(lists : ArrayList<List>){
        presenter.setPrivateLists(lists)
    }

*/

