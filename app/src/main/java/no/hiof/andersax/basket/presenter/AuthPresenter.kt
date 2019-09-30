package no.hiof.andersax.basket.presenter

import com.google.firebase.auth.FirebaseAuth
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.UserActions
import no.hiof.andersax.basket.model.User


class AuthPresenter{
    private var userAuth =  FirebaseAuth.getInstance()
    private var useractions : UserActions = UserActions()
    private var isSignInSuccess : Boolean = false
    private var isSignUpSuccess : Boolean = false


    fun SignInUser(email : String, password : String):Boolean {
        try {
            userAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { Task ->
                    isSignInSuccess = Task.isSuccessful

                }

        }catch(e : Exception){
           e.printStackTrace()
        }
        return isSignInSuccess

    }

    fun signUpNewUser(email: String, password: String, phone: String) : Boolean{
        var newUser = User(phone, email)
        userAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {Task ->
               isSignUpSuccess = Task.isSuccessful
            }

        useractions.addUserToDb(newUser, userAuth.uid!!)

        return isSignUpSuccess
     }




}