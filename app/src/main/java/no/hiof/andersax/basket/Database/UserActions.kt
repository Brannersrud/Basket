package no.hiof.andersax.basket.Database

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.IuserList
import no.hiof.andersax.basket.model.List
import no.hiof.andersax.basket.model.User;



class UserActions{
    val db = FirebaseFirestore.getInstance()

    fun addUserToDb(user : User, uid: String){

        db.collection("Users").document("${user.email}")
            .set(user)
            .addOnSuccessListener {
                Log.d("auth", "added user to db")
            }
            .addOnFailureListener {
                Log.d("auth", "someshit")
            }
    }

    fun getAllUsers(){

    }

}