package no.hiof.andersax.basket.Database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthActions {
    private var userAuth =  FirebaseAuth.getInstance()



    fun getAuth () : FirebaseAuth {
        return userAuth
    }

    fun getCurrentUser() : FirebaseUser {
        return userAuth.currentUser!!
    }

}