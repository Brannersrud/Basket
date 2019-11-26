package no.hiof.andersax.basket.presenter

import android.os.Handler
import android.util.Log
import androidx.navigation.NavHost
import com.google.firebase.auth.FirebaseAuth
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.UserActions
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.view.createUserFragment
import no.hiof.andersax.basket.view.loginFragment
import java.lang.Exception


class AuthPresenter {
    private var userAuth = FirebaseAuth.getInstance()
    private var useractions: UserActions = UserActions()


    fun signUpNewUser(email: String, password: String, phone: String, username : String, fragment : createUserFragment): Boolean {
        var isSignUpSuccess = false
        val newUser = User(phone, email)
        try {
            userAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { Task ->
                    if(Task.isSuccessful) {
                        isSignUpSuccess = Task.isSuccessful
                        useractions.addUserToDb(newUser, username, fragment)
                    }else{
                        fragment.showToastToUser("account could not be created, try with another email")

                    }

                }
        }catch (e : Exception){
            fragment.showToastToUser("Your internet might be a sparce resource")
        } finally {
            return isSignUpSuccess
        }

    }

    fun getCurrentLoggedInUser() : String {
        val ref = useractions.db.collection("Users")
        var myLoggedInUser = ""
        try {
            ref.whereEqualTo("email", userAuth.currentUser!!.email)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result!!
                            .asSequence()
                            .forEach { it ->
                                myLoggedInUser = it.id

                            }

                    }
                }
        }catch (e : Exception){
            e.printStackTrace()
        }finally {
            return myLoggedInUser

        }

    }
}




