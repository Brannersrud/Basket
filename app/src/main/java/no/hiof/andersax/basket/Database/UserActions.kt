package no.hiof.andersax.basket.Database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.model.User;
import no.hiof.andersax.basket.presenter.UserPresenter
import no.hiof.andersax.basket.view.addFriendsFragment


class UserActions{
    val db = FirebaseFirestore.getInstance()

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

    fun getAllUsers(target : addFriendsFragment){
        val users : MutableList<String> = ArrayList<String>()
        db.collection("Users")
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    task.result!!
                        .asSequence()
                        .forEach {
                            users.add(it.id)

                        }
                    target.setUpSingleListRecyclerView(users)
                }
            }
    }
}