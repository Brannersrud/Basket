package no.hiof.andersax.basket.presenter

import com.google.firebase.auth.FirebaseAuth
import no.hiof.andersax.basket.model.User


class AuthPresenter{
    private var userAuth =  FirebaseAuth.getInstance()
    private var isSignUpSuccess : Boolean = false
    private var isSignInSuccess : Boolean = false


    fun SignInUser(email : String, password : String):Boolean {
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { Task ->
                isSignInSuccess = Task.isSuccessful

            }
        return isSignInSuccess
    }
    fun signUpNewUser(email: String, password: String, phone: String) : Boolean{
        userAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {Task ->
               isSignUpSuccess = Task.isSuccessful
            }
        return isSignUpSuccess
     }




}