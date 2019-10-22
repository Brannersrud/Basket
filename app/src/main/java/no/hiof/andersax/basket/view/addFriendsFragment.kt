package no.hiof.andersax.basket.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_add_friends.*
import kotlinx.android.synthetic.main.fragment_private_list.*
import kotlinx.android.synthetic.main.fragment_private_list.view.*
import kotlinx.android.synthetic.main.user_search_layout.*
import no.hiof.andersax.basket.Adapter.SearchAdapter
import no.hiof.andersax.basket.Adapter.listItemAdapter
import no.hiof.andersax.basket.Database.UserActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.presenter.UserPresenter

/**
 * A simple [Fragment] subclass.
 */
class addFriendsFragment : Fragment() {
    private var useractions : UserActions = UserActions()
    private var searchables : MutableList<String> = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        useractions.getAllUsers(this)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_friends, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var searchresults = searchIdRecyclerView;
        var submitbtn = buttonSubmit;
        var searchQuery = searchForFriend
        var searchButton = searchIconButton
        searchQuery.addTextChangedListener{
            if(searchQuery.text.toString().isNotEmpty()) {
                handleSearch(searchQuery.text.toString())
            }
        }

       /* searchButton.setOnClickListener{
           firebaseUserSearch(searchForFriend.text.toString());
        }
        */

    }

     fun setUpSingleListRecyclerView(userlist : MutableList<String>) {
       searchIdRecyclerView.adapter = SearchAdapter(userlist,
           View.OnClickListener { view ->
             val position = searchIdRecyclerView.getChildAdapterPosition(view)
               val clickedItem = userlist[position]
               print(clickedItem)
           }
           )
        searchIdRecyclerView.layoutManager = GridLayoutManager(context,1)
    }



    private fun handleSearch(query : String) {
        Log.d("serrr", searchables.toString())


    }


}
