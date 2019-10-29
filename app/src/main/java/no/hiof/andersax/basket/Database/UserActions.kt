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


}