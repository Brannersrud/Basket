package no.hiof.andersax.basket.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_user.*
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.presenter.AuthPresenter

/**
 * A simple [Fragment] subclass.
 */
class createUserFragment : Fragment() {
    private var presenter : AuthPresenter = AuthPresenter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar!!.hide()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCreateUser.setOnClickListener {
            val email = createUserEmailInput.text.toString()
            val password = createUserPasswordInput.text.toString()
            val passwordretype = createUserRetypePasswordInput.text.toString()
            val username = userNameSignup.text.toString()
            val phone = createUserPhonenumberInput.text.toString()
            handleUserCreation(email, password, passwordretype,phone, username)
        }
    }





    private fun handleUserCreation(email: String, password: String, passwordretype: String, phone: String, username : String) {
        //validating, some user input

        if(!email.isEmpty() && !password.isEmpty() && !phone.isEmpty() && password.equals(passwordretype) && username.isNotEmpty()){
           if(presenter.signUpNewUser(email, password, phone, username)){
               var action = createUserFragmentDirections.actionCreateUserFragmentToLoginFragment()
               findNavController().navigate(action)
           }else{
               println("could not compute?")
           }
        }

    }




}
