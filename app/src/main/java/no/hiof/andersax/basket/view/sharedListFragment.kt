package no.hiof.andersax.basket.view


import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_shared_list.*
import no.hiof.andersax.basket.Adapter.listItemAdapter
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter
import no.hiof.andersax.basket.presenter.UserPresenter
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class sharedListFragment : Fragment() {
    private var presenter : ListPresenter = ListPresenter()
    private var listname: String = ""
    private var listdescription: String = ""
    private var owner: String = ""
    private var id: String = ""
    private var totalPrice : Long = 0
    private var memberCount : Long = 0
    private val userPresenter : UserPresenter = UserPresenter()
    private var priceExpected : Long = 0
    private var Auth : AuthActions = AuthActions()
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
            memberCount = args.getLong("members")

        }
        presenter.getListItems(id, this, false, "sharedList")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedOwnerLabel.text = context!!.getString(R.string.ownerLabel, "Owner: ", this.owner)
        sharedDescriptionLabel.text = this.listdescription
        sharedListName.text = listname
        listTotalAmount.text = context!!.getString(R.string.total_price_label, "totalprice: ", this.totalPrice.toString())
        val sharedListButton = addItemToSharedListButton



        if(!owner.equals(Auth.getCurrentUser().email)) {
            if (memberCount.toInt() == 1) {
                youOweLabel.text = context!!.getString(R.string.you_owe_label, "you owe: ", (totalPrice/2).toString())
                priceExpected = (totalPrice / 2)

            } else if (memberCount > 1) {
                youOweLabel.text = context!!.getString(R.string.you_owe_label, "you owe: ", (totalPrice/memberCount).toString())
                priceExpected = (totalPrice / memberCount)
            } else {
                youOweLabel.text = context!!.getString(R.string.you_owe_label, "You owe: ", "nothing yet")
            }
        }else{
            youOweLabel.text = ""
            listTotalAmount.text=""
        }


        if(!owner.equals(Auth.getCurrentUser().email)) {
            paymentButton.setOnClickListener {
                handlePayMent()
            }
        }else{
            paymentButton.setImageResource(android.R.color.transparent)
        }



        buttonUpdate.setOnClickListener {
                presenter.updateSharedList(this, id, "slow")

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

    override fun onDestroy() {
        super.onDestroy()
        presenter.updateSharedList(this, id, "destroy")

    }

    private fun handlePayMent(){
      val build :  AlertDialog.Builder = AlertDialog.Builder(context)
        build.setTitle("Payment")
        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        build.setView(input)
        build.setCancelable(true)


        build.setPositiveButton("Pay",  { dialogInterface, i ->
            val valuePaid = input.text.toString().toLong()
            if(priceExpected < 1){
                showToastToUser("No payment registerd")
            }
            else if(valuePaid == priceExpected){
                showToastToUser("Successfull")
               userPresenter.handlePayMentForUser(id, valuePaid, listname, Date(), "shared")

            }else if(valuePaid < priceExpected){
                showToastToUser("$valuePaid is to low when expected is $priceExpected")
            }else if(valuePaid > priceExpected){
                showToastToUser("you don't have to pay that much, we only want $priceExpected")
            }
        })

        build.setNegativeButton("Cancel",  { dialogInterface, i ->
            print("ive been canceled")
        })

        build.show()

    }


    private fun handleListItemAdd(itemName: String, qt: Long) {
        val listitem = ListItem(itemName, qt, false, 0)
        presenter.addItemToSharedList(listitem, this)
        noSharedListItemsLabel.text = ""
    }


    fun setUpSharedRecyclerView() {
        val listitems = presenter.getCurrentSharedList()
        if(listitems.isNotEmpty()) {
            sharedListItemRecyclerView.adapter = listItemAdapter(listitems.asReversed())
            sharedListItemRecyclerView.layoutManager = GridLayoutManager(context, 1)
        }else{
            noSharedListItemsLabel.text = context!!.getString(R.string.no_shared_list_message)
        }
    }




    fun showToastToUser(message : String){
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(context, message, duration)

        toast.show()

    }

}
