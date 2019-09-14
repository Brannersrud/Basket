package no.hiof.andersax.basket.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import no.hiof.andersax.basket.R


/**
 * A simple [Fragment] subclass.
 */
class loginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getActivity()!!.setTitle("Login")
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goToCreateUser.setOnClickListener {
            val createUserAction = loginFragmentDirections.actionLoginFragmentToCreateUserFragment()
            findNavController().navigate(createUserAction)
        }

        loginButton.setOnClickListener {
            val listOverviewAction = loginFragmentDirections.actionLoginFragmentToListOverviewFragment2()
            findNavController().navigate(listOverviewAction)
        }
    }
}
