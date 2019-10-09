package no.hiof.andersax.basket.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_private_list.*
import kotlinx.android.synthetic.main.fragment_private_list.view.*
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
    private var store : FirebaseFirestore  = FirebaseFirestore.getInstance()
    private var currentList : MutableList<ListItem> = ArrayList()
    private var presenter : ListPresenter = ListPresenter()



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

        }
        getListItems(id)
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
            if (qt !== null && itemText !== "") {
                val desiredValue: Long = qt.toLong()
                addListItem(desiredValue, itemText)
            }else{
                println("error")
            }
        }

    }

    fun addListItem(qt: Long, itemname: String) {
        //need to push this to the a mutablelist then rerender recyclerview
        val listitem = ListItem(itemname, qt, false, 0)
        presenter.addItemToList(listitem)
        setUpSingleListRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.addPrivateList(listname,listdescription,owner,0, id)
    }



    private fun setUpSingleListRecyclerView() {
        var list = presenter.getCurrentList()
           scrollViewPrivateList.privateListRecyclerView.adapter = listItemAdapter(list)
            privateListRecyclerView.layoutManager = GridLayoutManager(context, 1)
        }


    fun getListItems(id: String) {
        val ref = store.collection("privateList")
        ref.get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    task.result!!
                        .asSequence()
                        .filter { it.id == id }
                        .forEach {
                            var data = it.data["items"] as List<HashMap<String, Any>>
                            prepareData(data)
                        }
                }else{
                    error("Should i have an error field instead of posts?")
                }
            }
    }

    private fun prepareData(list: List<HashMap<String, Any>>) {
        var items : MutableList<ListItem> = ArrayList()
        var i : Int = 0;
        for(item in list){
            var p = list.get(i)["price"] as Long
            var n = list.get(i)["itemName"] as String
            var c = list.get(i)["checked"] as Boolean
            var q = list.get(i)["quantity"] as Long
            items.add(ListItem(n,q,c,p))
            i++;

        }
        presenter.addCurrentList(items)
        setUpSingleListRecyclerView()
    }


}






