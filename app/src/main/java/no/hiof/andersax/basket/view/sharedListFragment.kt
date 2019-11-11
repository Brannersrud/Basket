package no.hiof.andersax.basket.view


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_shared_list.*
import no.hiof.andersax.basket.Adapter.listItemAdapter
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter
import no.hiof.andersax.basket.presenter.UserPresenter

/**
 * A simple [Fragment] subclass.
 */
class sharedListFragment : Fragment() {
    private var presenter : ListPresenter = ListPresenter()
    private var store : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var listname: String = ""
    private var listdescription: String = ""
    private var owner: String = ""
    private var id: String = "";
    private var totalPrice : Long = 0;
    private var memberCount : Long = 0;
    private val userPresenter : UserPresenter = UserPresenter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        if (args != null) {
            listname = args.getString("listname")!!
            listdescription = args.getString("listdescription")!!
            id = args.getString("uid")!!
            owner = args.getString("owner")!!
            totalPrice = args.getLong("totalPrice")
            memberCount = args.getLong("members")!!

        }
        getListItems(id)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedOwnerLabel.text = "owner: " + this.owner
        sharedDescriptionLabel.text = this.listdescription
        sharedListName.text = this.listname
        listTotalAmount.text = "total price: " + this.totalPrice.toString()
        val sharedListButton = addItemToSharedListButton


        if(totalPrice.compareTo(1) == 1){
            println(memberCount)
            youOweLabel.text ="you owe: "+ (totalPrice/(memberCount)).toString()
        }
        paymentButton.setOnClickListener {
            handlePayMent()
        }


        buttonUpdate.setOnClickListener {
                presenter.updateSharedList(this, id)

        }

        sharedListButton.setOnClickListener {
            if (sharedListItemName.text.toString().isNotEmpty() && sharedListItemQuantity.text.toString().isNotEmpty()) {
                handleListItemAdd(
                    sharedListItemName.text.toString(),
                    sharedListItemQuantity.text.toString().toLong()
                )

            }
        }
    }

    private fun handlePayMent(){
      var build :  AlertDialog.Builder = AlertDialog.Builder(context)
        build.setTitle("This is the title")
        var input : EditText = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        build.setView(input)
        build.setCancelable(true)


        build.setPositiveButton("Pay", DialogInterface.OnClickListener { dialogInterface, i ->
            var valuePaid = input.text.toString().toLong()
            var priceExpected = (totalPrice/memberCount)

            if(valuePaid == priceExpected){
                showToastToUser("Successfull")
               userPresenter.handlePayMentForUser(id, valuePaid, listname)

            }else if(valuePaid < priceExpected){
                showToastToUser("$valuePaid is to low when expected is $priceExpected")
            }else if(valuePaid > priceExpected){
                showToastToUser("you don't have to pay that much, we only want $priceExpected")
            }
        })

        build.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
            print("ive been canceled")
        })

        build.show()

    }


    private fun handleListItemAdd(itemName: String, qt: Long) {
        val listitem = ListItem(itemName, qt, false, 0)
        presenter.addItemToSharedList(listitem, this)
    }


    fun setUpSharedRecyclerView(list : MutableList<ListItem>) {
        sharedListItemRecyclerView.adapter = listItemAdapter(list)
        sharedListItemRecyclerView.layoutManager = GridLayoutManager(context, 1)
    }

    private fun getListItems(id: String) {
        val ref = store.collection("sharedList")
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
        presenter.addCurrentSharedList(items)

        setUpSharedRecyclerView(items)

    }


    fun showToastToUser(message : String){
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(context, message, duration)

        toast.show()

    }

}
