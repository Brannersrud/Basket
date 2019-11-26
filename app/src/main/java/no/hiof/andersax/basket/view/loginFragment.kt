package no.hiof.andersax.basket.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import no.hiof.andersax.basket.presenter.AuthPresenter
import no.hiof.andersax.basket.presenter.ListPresenter
import java.lang.Thread.sleep
import androidx.appcompat.app.AppCompatActivity
import no.hiof.andersax.basket.Onboarding.OnBoardingActivity
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.services.sharedPreferencesManipulator


/**
 * A simple [Fragment] subclass.
 */
class loginFragment : Fragment() {
    private var userAuth = FirebaseAuth.getInstance()
    private var prefs : sharedPreferencesManipulator = sharedPreferencesManipulator()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(no.hiof.andersax.basket.R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(context!!)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if(prefs.restoredPrefData(context!!, "myPrefs", "isIntroFinished")){
            navigateToNextScreen()
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val createUserAction = loginFragmentDirections.actionLoginFragment2ToCreateUserFragment2()
        val loginbtn : Button = loginButton
        goToCreateUser.setOnClickListener {
            findNavController().navigate(createUserAction)
        }
        loginbtn.setOnClickListener {
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
                        print("Ja?")
                        Log.d("result", Task.result.toString())
                        savePrefsData()

                        navigateToNextScreen()
                    } else {
                        errorMessageLogin.setText(R.string.error_message_login)
                    }
                }
        }else{
            errorMessageLogin.setText(R.string.error_message_need_to_fill_out)
        }
    }


    fun navigateToNextScreen(){
        val intent = Intent(context,OnBoardingActivity::class.java )

        startActivity(intent)

    }

    private fun savePrefsData(){
        prefs.savePrefsData(context!!, "myPrefs", "isIntroFinished")
    }


}
