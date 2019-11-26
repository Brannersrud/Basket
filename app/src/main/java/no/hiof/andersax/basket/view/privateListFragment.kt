package no.hiof.andersax.basket.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_private_list.*
import no.hiof.andersax.basket.Adapter.listItemAdapter
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter
import no.hiof.andersax.basket.R


/**
 * A simple [Fragment] subclass.
 */
class privateListFragment : Fragment() {
    private var listname: String = ""
    private var listdescription: String = ""
    private var owner: String = ""
    private var id: String = "";
    private var presenter : ListPresenter = ListPresenter()
    private var totalPrice : Long = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = arguments
        if (args != null) {
            listname = args.getString("name")!!
            listdescription = args.getString("description")!!
            owner = args.getString("Owner")!!
            id = args.getString("uid")!!
            totalPrice = args.getLong("totalPrice")

        }
        presenter.getListItems(id, this)
        return inflater.inflate(R.layout.fragment_private_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        privateListDescriptionField.text = listdescription
        //privateListOwnerField.text = "owner: " + owner
        privateListName.text = listname



        applyprivateChangeButton.setOnClickListener {
            presenter.addPrivateList(listname, listdescription, owner, id, this)
        }

        addNewItemPrivate.setOnClickListener {
            //presenter.addPrivateList(listname,listdescription,owner,0, id, this)
            if(addItemQuantityField.text.toString().isNotEmpty() && itemNameField.text.toString().isNotEmpty()) {
                addListItem(
                    addItemQuantityField.text.toString().toLong(),
                    itemNameField.text.toString()
                )
            }
        }

    }

    private fun addListItem(qt: Long, itemname: String) {
        //need to push this to the a mutablelist then rerender recyclerview
        val listitem = ListItem(itemname, qt, false, 0)
        presenter.addItemToList(listitem, this)
        noPrivateListItemsLabel.text=""

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }



     fun setUpSingleListRecyclerView() {
        var list = presenter.getCurrentList()

         if(list.isNotEmpty()) {
             privateListRecyclerView.adapter = listItemAdapter(list.asReversed())
             privateListRecyclerView.layoutManager = GridLayoutManager(context, 1)
         }else{
             noPrivateListItemsLabel.text = "No items yet.. add some"
         }

     }




    fun showToastToUser(message : String, shouldNavigate : Boolean){
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(context, message, duration)

        toast.show()


        if(shouldNavigate){
            val action = privateListFragmentDirections.actionPrivateListFragmentToListOverviewFragment2()

            findNavController().navigate(action)
        }

    }

}






