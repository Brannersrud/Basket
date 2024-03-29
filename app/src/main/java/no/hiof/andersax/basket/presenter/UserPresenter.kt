package no.hiof.andersax.basket.presenter

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.UserActions
import no.hiof.andersax.basket.model.ListHistoryItem
import no.hiof.andersax.basket.model.ListMembers
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.model.sharedList
import no.hiof.andersax.basket.profileActivity
import no.hiof.andersax.basket.statisticsActivity
import no.hiof.andersax.basket.view.createListFragment
import java.util.*
import kotlin.collections.ArrayList

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
            }.addOnFailureListener { e ->
                e.suppressed
            }
    }




    fun handlePayMentForUser(id : String, pricePaid : Long, listname : String, date : Date, isPrivate : String){
        db.collection("Users")
            .whereEqualTo("email", authActions.getCurrentUser().email)
            .get().addOnCompleteListener { it ->
                if(it.isSuccessful){
                    it.result!!
                        .asSequence()
                        .forEach {
                            if(isPrivate.equals("shared")) {
                                uactions.markUserAsPaid(id, pricePaid, it.id)
                            }
                            uactions.insertHistoryItem(
                                pricePaid,
                                listname,
                                it.id,
                                date,
                                isPrivate
                            )
                        }
                }
            }.addOnFailureListener { e ->

            }
    }

    fun getProfileInformation(activity : profileActivity) {
        val db = authActions.getFireBaseStoreReference()
        val refPriv = db.collection("privateList")
        val refShared = db.collection("sharedList")
        val usersref = db.collection("Users")
        var sharedres: Int
        refShared.whereEqualTo("owner", authActions.getCurrentUser().email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sharedres = task.result!!.size()
                    activity.setUpSharedCount(sharedres)
                }
            }
        var privRes : Int
        refPriv.whereEqualTo("owner", authActions.getCurrentUser().email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    privRes = task.result!!.size()
                    activity.setUpPrivateCount(privRes)
                }
            }.addOnFailureListener { e ->
                e.suppressed
            }

        var name = ""
        usersref.whereEqualTo("email", authActions.getCurrentUser().email)
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



    fun getHistoryForUser(activity: profileActivity, uname : String){
        val listHistory : MutableList<ListHistoryItem> = ArrayList<ListHistoryItem>()
        db.collection("Users").document(uname)
            .collection("History")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                        val obj = document.toObject(ListHistoryItem::class.java)
                        listHistory.add(obj)


                }
                activity.setHistoryForUser(listHistory)
            }.addOnFailureListener { e ->
                e.printStackTrace()
            }

        //update activity
    }


}