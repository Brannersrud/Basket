package no.hiof.andersax.basket.Database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.model.ListHistoryItem
import no.hiof.andersax.basket.model.ListMembers
import no.hiof.andersax.basket.model.User;
import no.hiof.andersax.basket.model.sharedList
import no.hiof.andersax.basket.view.createListFragment
import java.util.*


class UserActions{
    val db = FirebaseFirestore.getInstance()
    val authActions : AuthActions = AuthActions()

    fun addUserToDb(user : User, uid: String, username : String){
        db.collection("Users").document("${username}")
            .set(user)
            .addOnSuccessListener {
                Log.d("auth", "added user to db")
            }
            .addOnFailureListener {
                Log.d("auth", "someshit")
            }
    }


    fun insertHistoryItem(pricePaid: Long, listname: String, username : String, date : Date, isPrivate : String){
        val item = ListHistoryItem(pricePaid, listname, date, isPrivate)
        db.collection("Users").document(username).collection("History")
            .add(item).addOnFailureListener { e ->
                e.suppressed
            }
    }

    fun markUserAsPaid(id : String, pricePaid: Long, listname: String, username : String){
        val members : MutableList<ListMembers> = ArrayList<ListMembers>()
        db.collection("sharedList").document(id)
            .get()
            .addOnSuccessListener { doc ->
                val list = doc.toObject(sharedList::class.java)

                for(m in list!!.members){
                    if(m.username == username){
                        m.amountpaid = pricePaid
                        m.checked = true
                        m.hasPaid = true

                        members.add(m)
                    }
                }
            }.continueWith {
                db.collection("sharedList").document(id)
                    .update("members", members)
            }.addOnFailureListener { e ->
                e.suppressed
            }
    }



}