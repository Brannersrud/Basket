package no.hiof.andersax.basket.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_create_list.*
import kotlinx.android.synthetic.main.fragment_create_list.searchIdRecyclerView
import no.hiof.andersax.basket.Adapter.SearchAdapter
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.Database.UserActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.model.ListMembers
import no.hiof.andersax.basket.presenter.AuthPresenter
import no.hiof.andersax.basket.presenter.ListPresenter
import no.hiof.andersax.basket.presenter.UserPresenter

/**
 * A simple [Fragment] subclass.
 */
class createListFragment : Fragment() {
    private var listpresenter : ListPresenter = ListPresenter()
    private var actions : AuthActions = AuthActions()
    private var userpresenter : UserPresenter = UserPresenter()
    private var addedToList : MutableList<ListMembers> = ArrayList<ListMembers>()
    private var searchables : MutableList<ListMembers> = ArrayList<ListMembers>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userpresenter.getAllUsers(this)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var searchQuery = searchForFriend2



        searchQuery.addTextChangedListener {
            handleSearchQuery(searchQuery.text.toString())
        }

        createListButton.setOnClickListener {
            var listName = createListNameField.text.toString()
            var listDescription = createListDescriptionField.text.toString()
            handleRedirect(listName, listDescription)
        }

    }

    private fun handleRedirect(listName: String, listDescription: String) {
        if(listName.isNotEmpty() && listDescription.isNotEmpty() && this.addedToList.size == 0){

            val action = createListFragmentDirections.actionCreateListFragmentToPrivateListFragment(actions.getCurrentUser().email!!,listName, listDescription, actions.getCurrentUser().uid)
            //pass data with navigate, next scene makes the list.
            //presenter.addPrivateList(actions.getCurrentUser().uid, listName, listDescription)
            findNavController().navigate(action)

        }else if(listName.isNotEmpty() && listDescription.isNotEmpty() && this.addedToList.size > 0){
            //add shared list to db
            val emptylist :  MutableList<ListItem> = ArrayList<ListItem>()
            listpresenter.addSharedList(this,actions.getCurrentUser().email!!, listName, listDescription, this.addedToList, emptylist, 0, "")
           // val action = createListFragmentDirections.actionCreateListFragmentToListOverviewFragment2()
           //findNavController().navigate(action)

        }else{
            errorCreateList.text = "You have to fill out listname \n and description to create a list"
        }
    }

    private fun handleSearchQuery(query: String) {
        var list : MutableList<ListMembers> = ArrayList<ListMembers>()
        if(query.length > 3){
            for(item in searchables){
                if(item.username.contains(query)){
                    list.add(item)
                }
            }

            setUpSingleListRecyclerView(list)
        }else if(query.length < 3){
            list.clear()
            setUpSingleListRecyclerView(list)
        }

    }
    private fun handleAddUserToList(member : ListMembers){
        var size = listsize
        if(!addedToList.contains(member)){
        addedToList.add(member)
        }
        size.text="List size: you and  " +this.addedToList.size.toString() + " others"
    }

    fun setSearchAbles(list : MutableList<ListMembers>){

        searchables.addAll(list)
    }
    private fun setUpSingleListRecyclerView(userlist : MutableList<ListMembers>) {
        searchIdRecyclerView.adapter = SearchAdapter(userlist,
            View.OnClickListener { view ->
                val position = searchIdRecyclerView.getChildAdapterPosition(view)
                val clickedItem = userlist[position]

                handleAddUserToList(clickedItem)

            }
        )
        searchIdRecyclerView.layoutManager = GridLayoutManager(context,1)
    }

    fun showToastToUser(message : String){
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(context, message, duration)

        toast.show()

    }


}
