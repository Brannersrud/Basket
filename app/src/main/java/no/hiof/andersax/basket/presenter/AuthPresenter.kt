package no.hiof.andersax.basket.presenter

import android.os.Handler
import android.util.Log
import androidx.navigation.NavHost
import com.google.firebase.auth.FirebaseAuth
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.UserActions
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.view.loginFragment


class AuthPresenter {
    private var userAuth = FirebaseAuth.getInstance()
    private var useractions: UserActions = UserActions()






    fun signUpNewUser(email: String, password: String, phone: String): Boolean {
        var isSignUpSuccess = false
        var newUser = User(phone, email)
        try {
            userAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { Task ->
                    isSignUpSuccess = Task.isSuccessful
                    useractions.addUserToDb(newUser, userAuth.uid!!)

                }
        } finally {
            return isSignUpSuccess
        }

    }
}




