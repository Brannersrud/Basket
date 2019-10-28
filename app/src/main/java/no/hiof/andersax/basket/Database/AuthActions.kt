package no.hiof.andersax.basket.Database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AuthActions {
    private val userAuth =  FirebaseAuth.getInstance()
    private val firebasestore : FirebaseFirestore = FirebaseFirestore.getInstance()



    fun getAuth () : FirebaseAuth {
        return userAuth
    }

     fun getCurrentUser() : FirebaseUser {
        return userAuth.currentUser!!
    }
    fun getFireBaseStoreReference() : FirebaseFirestore{
        return this.firebasestore
    }


}