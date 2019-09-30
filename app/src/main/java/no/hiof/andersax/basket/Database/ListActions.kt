package no.hiof.andersax.basket.Database

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import no.hiof.andersax.basket.model.List
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter

class ListActions {
    private var Auth : AuthActions = AuthActions()


    fun addPrivateList(list : List){
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")
        ref.add(list)
            .addOnSuccessListener {
                Log.d("successfull", "jyeee")
            }
    }

    fun getPrivateLists(){
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")
        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnCompleteListener { documents ->

                
            }
    }
}
/*
    fun setPrivateLists(lists : ArrayList<List>){
        presenter.setPrivateLists(lists)
    }

*/

