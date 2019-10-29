package no.hiof.andersax.basket.Database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.model.sharedList
import no.hiof.andersax.basket.view.createListFragment
import no.hiof.andersax.basket.view.privateListFragment
import no.hiof.andersax.basket.view.sharedListFragment

class ListActions {
    private var Auth: AuthActions = AuthActions()


    fun addPrivateList(list: ListCollection, uid: String, fragment: privateListFragment) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("privateList").document(uid)
        ref.get()
            .addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    overWritePrivateList(uid, list,fragment)
                }else{
                    addNewPrivateList(list, fragment)
                }
            }
    }

    private fun addNewPrivateList(list: ListCollection, fragment: privateListFragment) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("privateList")

        ref.add(list)
            .addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    fragment.showToastToUser("List added beautifully")
                }else{
                    fragment.showToastToUser("List rejected violently")
                }
            }

    }
    private fun overWritePrivateList(uid: String, list: ListCollection, fragment: privateListFragment) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("privateList").document(uid)
        ref.update("items", list.getListItems())
            .addOnCompleteListener {Task ->
                if(Task.isSuccessful){
                    fragment.showToastToUser("List updated gracefully")
                }else{
                    fragment.showToastToUser("Unable to update list, obnoxiously")
                }
            }
    }



     fun overWriteSharedList(uid: String, list: MutableList<ListItem>, fragment: sharedListFragment, totalprice : Long) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("sharedList").document(uid)

        ref.update("items", list)
            .addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    fragment.showToastToUser("Updated list... fabolously")
                    ref.update("totalPrice", totalprice)
                }else{
                    fragment.showToastToUser("Could not update.. tragicly")
                }
            }



    }

     fun addSharedList(list:sharedList, fragment:sharedListFragment){
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("sharedList")

        ref.add(list)
            .addOnCompleteListener { Task ->
                if (Task.isSuccessful) {
                    //do some fancy stuff, toast for user
                    fragment.showToastToUser("List created carefully")
                } else {
                    fragment.showToastToUser("List denied awfully")
                }
            }
    }




}



