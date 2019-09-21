package no.hiof.andersax.basket.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.presenter.AuthPresenter

/**
 * A simple [Fragment] subclass.
 */
class loginFragment : Fragment() {
    private var presenter : AuthPresenter = AuthPresenter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getActivity()!!.setTitle("Login")
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToCreateUser.setOnClickListener {
            val createUserAction = loginFragmentDirections.actionLoginFragmentToCreateUserFragment()
            findNavController().navigate(createUserAction)
        }

        loginButton.setOnClickListener {
           login(usernameLoginField.text.toString(), passwordLoginField.text.toString())
        }
    }

    fun goToListView() {
        val loginAction = loginFragmentDirections.actionLoginFragmentToListOverviewFragment2()
        findNavController().navigate(loginAction)
    }

    fun login(email : String, password : String){
        Log.d("try", "reached")
        if(email !== "" && password !== ""){
          if(presenter.SignInUser(email, password)){
              goToListView()
          }

        }
    }
}
