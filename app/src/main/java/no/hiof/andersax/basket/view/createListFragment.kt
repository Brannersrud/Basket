package no.hiof.andersax.basket.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_list.*
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.presenter.ListPresenter

/**
 * A simple [Fragment] subclass.
 */
class createListFragment : Fragment() {
    private var presenter : ListPresenter = ListPresenter()
    private var actions : AuthActions = AuthActions()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val iconNext = addFriendIcon;
        iconNext.setOnClickListener {
            val action = createListFragmentDirections.actionCreateListFragmentToAddFriendsFragment();
            findNavController().navigate(action);
        }


        createListButton.setOnClickListener {
            var listName = createListNameField.text.toString()
            var listDescription = createListDescriptionField.text.toString()

            if(listName.isNotEmpty() && listDescription.isNotEmpty()){

               val action = createListFragmentDirections.actionCreateListFragmentToPrivateListFragment(listName, listDescription,actions.getCurrentUser().email!!, "empty")
                //pass data with navigate, next scene makes the list.
                //presenter.addPrivateList(actions.getCurrentUser().uid, listName, listDescription)
                findNavController().navigate(action)

            }
        }



    }
}
