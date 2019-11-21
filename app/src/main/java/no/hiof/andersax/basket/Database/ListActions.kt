package no.hiof.andersax.basket.Database

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
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
                    val taskResult = Task.result
                    if(!taskResult!!.exists()){
                        addNewPrivateList(list, fragment)
                    }else {
                        overWritePrivateList(uid, list, fragment)
                    }
                }
            }.addOnFailureListener { e ->
               fragment.showToastToUser("something happened, try checking your internet connection", false)
            }
    }

    private fun addNewPrivateList(list: ListCollection, fragment: privateListFragment) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("privateList")

        ref.add(list)
            .addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    fragment.showToastToUser("List added beautifully", true)
                }else{
                    fragment.showToastToUser("List rejected violently",false)
                }
            }.addOnFailureListener { e ->
                e.printStackTrace()
            }

    }
    private fun overWritePrivateList(uid: String, list: ListCollection, fragment: privateListFragment) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("privateList").document(uid)
        var price : Long = 0

        for(i in list.items){
            price += i.price
        }
        ref.update("items", list.items)
            .addOnCompleteListener {Task ->
                if(Task.isSuccessful){
                    fragment.showToastToUser("List updated gracefully", true)
                    ref.update("totalPrice", price)

                }
                else{
                    fragment.showToastToUser("Unable to update list, obnoxiously", false)
                }
            }.addOnFailureListener {
                fragment.showToastToUser("Something happened, try checking your internet connection", false)

            }
    }



     fun overWriteSharedList(uid: String, list: MutableList<ListItem>, fragment: sharedListFragment, totalprice : Long, shouldShowToast : Boolean) {
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("sharedList").document(uid)
         var price : Long= 0;
         for(i in list){
             price+= i.price
         }

        ref.update("items", list)
            .addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    if(shouldShowToast) {
                        fragment.showToastToUser("Updated list... fabolously")
                    }
                    ref.update("totalPrice", price)
                }else{
                    if(shouldShowToast) {
                        fragment.showToastToUser("Could not update.. tragicly")
                    }
                }
            }.addOnFailureListener {
                if(shouldShowToast){
                    fragment.showToastToUser("Something happened, try checking your internet connection")
                }
            }



    }

     fun addSharedList(list:sharedList, fragment:createListFragment){
        val db = Auth.getFireBaseStoreReference()
        val ref = db.collection("sharedList")

        ref.add(list)
            .addOnCompleteListener { Task ->
                if (Task.isSuccessful) {
                    //do some fancy stuff, toast for user
                    fragment.showToastToUser("List created carefully", true)
                } else {
                    fragment.showToastToUser("List denied awfully", false)
                }
            }.addOnFailureListener {
                fragment.showToastToUser("Something happened, try checking your internet connection", false)
            }
    }





}



