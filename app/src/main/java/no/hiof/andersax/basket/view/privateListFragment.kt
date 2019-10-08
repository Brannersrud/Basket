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
    private var items: ArrayList<ListItem> = ArrayList()
    private var id: String = "";
    private var presenter : ListPresenter = ListPresenter()
    private var store : FirebaseFirestore  = FirebaseFirestore.getInstance()



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
        getListItems(id);
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
            if (qt !== null && itemText !== null) {
                val desiredValue: Long = qt.toLong()
                addListItem(desiredValue, itemText)
            }
        }

    }

    fun addListItem(qt: Long, itemname: String) {
        val listitem = ListItem(itemname, qt, false, 0)
        items.add(listitem)

    }

    override fun onDestroyView() {
        super.onDestroyView()
       // presenter.addPrivateList(listname, listdescription, owner, items)
    }

    private fun setUpSingleListRecyclerView(listitem: MutableList<ListItem>) {
        println(listitem)
           scrollViewPrivateList.privateListRecyclerView.adapter = listItemAdapter(listitem,
                View.OnClickListener { view ->
                    val position = privateListRecyclerView.getChildAdapterPosition(view)
                    val clickedList = items[position]

                }
            )
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
            items.add(ListItem(n,q,c,q))
            i++;

        }
        setUpSingleListRecyclerView(items)
    }


}






