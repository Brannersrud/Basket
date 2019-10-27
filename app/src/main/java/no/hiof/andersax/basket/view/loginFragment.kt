package no.hiof.andersax.basket.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.presenter.AuthPresenter
import no.hiof.andersax.basket.presenter.ListPresenter
import java.lang.Thread.sleep

/**
 * A simple [Fragment] subclass.
 */
class loginFragment : Fragment() {
    private var userAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(context!!)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        /*if(currentUser !== null){
            navigateToNextScreen()
        }*/

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val createUserAction = loginFragmentDirections.actionLoginFragmentToCreateUserFragment()
        goToCreateUser.setOnClickListener {
            findNavController().navigate(createUserAction)
        }
        loginButton.setOnClickListener {
            val email = usernameLoginField.text.toString()
            val password = passwordLoginField.text.toString()
            doLogin(email,password)
        }
    }

    fun doLogin(email : String, password : String){
        if(password.isNotEmpty() && email.isNotEmpty()){
            userAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { Task ->
                    if (Task.isSuccessful) {
                        navigateToNextScreen()
                    } else {
                        errorMessageLogin.text = "Could not authenticate"
                    }
                }
        }else{
            errorMessageLogin.text = "You need to fill out the required fields to log in"
        }
    }


    fun navigateToNextScreen(){
        var loginAction = loginFragmentDirections.actionLoginFragmentToListOverviewFragment2()
        findNavController().navigate(loginAction)

    }
}
