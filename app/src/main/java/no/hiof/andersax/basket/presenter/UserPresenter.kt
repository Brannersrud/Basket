package no.hiof.andersax.basket.presenter

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.UserActions
import no.hiof.andersax.basket.model.ListHistoryItem
import no.hiof.andersax.basket.model.ListMembers
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.view.createListFragment

class UserPresenter {
     val db : FirebaseFirestore = AuthActions().getFireBaseStoreReference()
        val authActions : AuthActions = AuthActions()
    private val uactions : UserActions = UserActions()
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
                                users.add(ListMembers(it.id, false,  false,0))

                        }
                    target.setSearchAbles(users)
                }
            }
    }




    fun handlePayMentForUser(id : String, pricePaid : Long, listname : String){

        db.collection("Users")
            .whereEqualTo("email", authActions.getCurrentUser().email)
            .get().addOnCompleteListener { it ->
                if(it.isSuccessful){
                    it.result!!
                        .asSequence()
                        .forEach {
                            insertHistoryItem(pricePaid, listname, it.id)
                            markUserAsPaid(id,pricePaid,listname, it.id)
                        }
                }
            }
    }

    private fun markUserAsPaid(id : String, pricePaid: Long, listname: String, username : String){
        val member = ListMembers(username, true, true, pricePaid)
        db.collection("sharedList").document(id)
            .update("members", member)
    }

    private fun insertHistoryItem(pricePaid: Long, listname: String, username : String){
        val item = ListHistoryItem(pricePaid, listname)
        db.collection("Users").document(username).collection("History")
            .add(item)
    }




}