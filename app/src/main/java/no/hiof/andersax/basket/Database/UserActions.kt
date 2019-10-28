package no.hiof.andersax.basket.Database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.model.ListMembers
import no.hiof.andersax.basket.model.User;
import no.hiof.andersax.basket.view.createListFragment


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