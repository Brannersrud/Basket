package no.hiof.andersax.basket.presenter

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.model.ListMembers
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.view.createListFragment

class UserPresenter {
     val db : FirebaseFirestore = AuthActions().getFireBaseStoreReference()
        val authActions : AuthActions = AuthActions()
    //getters and setters to useractions
    //search for users



    fun getAllUsers(target: createListFragment){
        val users : MutableList<ListMembers> = ArrayList<ListMembers>()
        db.collection("Users")
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    task.result!!
                        .asSequence()
                        .forEach {
                            if(!it.get("email")!!.equals(authActions.getCurrentUser().email))
                                users.add(ListMembers(it.id, false, 0, false))

                        }
                    target.setSearchAbles(users)
                }
            }
    }



}