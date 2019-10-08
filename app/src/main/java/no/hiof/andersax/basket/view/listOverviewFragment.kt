package no.hiof.andersax.basket.view


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_list_overview.*
import kotlinx.android.synthetic.main.fragment_private_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import no.hiof.andersax.basket.Adapter.ListOverviewAdapter
import no.hiof.andersax.basket.Adapter.listItemAdapter
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class listOverviewFragment : Fragment() {
    private val listp : ListPresenter = ListPresenter()
    private var Auth : AuthActions = AuthActions()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        getPrivateLists()
        return inflater.inflate(R.layout.fragment_list_overview, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addPrivateButton.setOnClickListener {
            val addListAction =
                listOverviewFragmentDirections.actionListOverviewFragment2ToCreateListFragment()
            findNavController().navigate(addListAction)
        }
        addSharedButton.setOnClickListener {
            val addListAction = listOverviewFragmentDirections.actionListOverviewFragment2ToCreateListFragment()
            findNavController().navigate(addListAction)
        }

    }


    fun setUpListRecyclerView(list : ArrayList<ListCollection>){
        singleListRecyclerView.adapter = ListOverviewAdapter(list,
                View.OnClickListener { view ->
                    val position = singleListRecyclerView.getChildAdapterPosition(view)
                    val clickedList = list[position]
                    //Action
                    val action =
                        listOverviewFragmentDirections.actionListOverviewFragment2ToPrivateListFragment(
                            clickedList.Owner,
                            clickedList.description,
                            clickedList.listname,
                            clickedList.getUid()
                        );
                    findNavController().navigate(action)

                }
            )
            singleListRecyclerView.layoutManager = GridLayoutManager(context, 1)

        }

    fun getPrivateLists() {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")
        var lists: ArrayList<ListCollection> = ArrayList()
        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var l: List<ListItem> = document.get("items")!! as List<ListItem>
                    var owner = document.get("owner").toString()
                    var listname = document.get("listname").toString()
                    var description = document.get("description").toString()
                    var listcollection = ListCollection(listname, description, owner, l)
                    listcollection.setUid(document.id)
                    lists.add(listcollection)
                    setUpListRecyclerView(lists);

                }
            }
    }
    }










