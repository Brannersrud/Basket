package no.hiof.andersax.basket.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_private_list.*
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.List
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter

/**
 * A simple [Fragment] subclass.
 */
class privateListFragment : Fragment() {
    private var listname : String = ""
    private var listdescription : String = ""
    private var owner : String = ""
    private var items : ArrayList<ListItem> = ArrayList()
    private var presenter : ListPresenter = ListPresenter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = arguments

        if (args != null) {
            listname = args.getString("listname")!!
            listdescription = args.getString("listdescription")!!
            owner = args.getString("owner")!!
        }

        return inflater.inflate(R.layout.fragment_private_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        privateListDescriptionField.text = listdescription
        privateListOwnerField.text = "owner: " + owner
        privateListName.text = listname


        applyChanges.setOnClickListener {
            val itemText = itemNameField.text.toString()
            val qt = addItemQuantityField.text.toString()
            if(qt !== null && itemText !== null){
                val desiredValue : Int = Integer.parseInt(qt)
                addListItem(desiredValue, itemText)
            }
        }

    }

    fun addListItem(qt : Int, itemname : String){
        val listitem = ListItem(itemname, qt, false, 0)
        items.add(listitem)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.addPrivateList(listname, listdescription,owner, items)
    }
}
