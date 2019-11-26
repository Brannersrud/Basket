package no.hiof.andersax.basket.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import no.hiof.andersax.basket.MainActivity
import no.hiof.andersax.basket.Onboarding.OnBoardingActivity
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.services.SharedPreferencesGateWay


/**
 * A simple [Fragment] subclass.
 */
class loginFragment : Fragment() {
    private var userAuth = FirebaseAuth.getInstance()
    private var prefs : SharedPreferencesGateWay = SharedPreferencesGateWay()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(context!!)

        if(prefs.restorePrefData(context!!, "myPrefs", "isIntroFinished")){
            navigateToMain()
        }else if(prefs.restorePrefData(context!!, "myPrefs", "hasLoggedIn")){
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

    private fun doLogin(email : String, password : String){
        if(password.isNotEmpty() && email.isNotEmpty()){
            userAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { Task ->
                    if (Task.isSuccessful) {
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


    private fun navigateToNextScreen(){
        val intent = Intent(context,OnBoardingActivity::class.java )

        startActivity(intent)

    }
   private fun navigateToMain(){
        val intent = Intent(context,MainActivity::class.java )

        startActivity(intent)
    }

    private fun savePrefsData(){
        prefs.savePrefsData(context!!, "myPrefs", "hasLoggedIn")
    }


}
