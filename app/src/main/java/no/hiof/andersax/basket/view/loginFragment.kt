package no.hiof.andersax.basket.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
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
        // Inflate the layout for this fragment
        getActivity()!!.setTitle("Login")
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
/*
       if(currentUser !== null){
           val loginAction = loginFragmentDirections.actionLoginFragmentToListOverviewFragment2()
           findNavController().navigate(loginAction)


       }*/

        return inflater.inflate(R.layout.fragment_login, container, false)
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
            if(email.isNotEmpty() && password.isNotEmpty()){
               doLogin(email, password)

            }
        }
    }

    fun doLogin(email : String, password : String){
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {Task->
                if(Task.isSuccessful){
                    navigateToNextScreen()
                }
            }
    }


    fun navigateToNextScreen(){
        var loginAction = loginFragmentDirections.actionLoginFragmentToListOverviewFragment2()
        findNavController().navigate(loginAction)

    }
}
