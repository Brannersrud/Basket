package no.hiof.andersax.basket.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_add_friends.*
import no.hiof.andersax.basket.Adapter.SearchAdapter
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.User

/**
 * A simple [Fragment] subclass.
 */
class addFriendsFragment : Fragment() {
    private lateinit var firestoreref : FirebaseFirestore;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_friends, container, false)

    }

    private fun firebaseUserSearch(strquery:String) {
        firestoreref = FirebaseFirestore.getInstance();
        val query : Query = firestoreref.collection("Users");
       val storeref : FirestoreRecyclerOptions<User> = FirestoreRecyclerOptions.Builder<User>()
           .setQuery(query, User::class.java).build();

        println(storeref.snapshots)

        //not really implemented yet. Wanted to finish the logic for updating a list first.



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var searchresults = searchIdRecyclerView;
        var submitbtn = buttonSubmit;
        var searchButton = searchIconButton
        searchButton.setOnClickListener{
           firebaseUserSearch(searchForFriend.text.toString());
        }


    }
}
