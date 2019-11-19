package no.hiof.andersax.basket.view


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_list_overview.*
import no.hiof.andersax.basket.Adapter.ListOverviewAdapter
import no.hiof.andersax.basket.Adapter.sharedListOverviewAdapter
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.User
import no.hiof.andersax.basket.model.sharedList
import no.hiof.andersax.basket.presenter.ListPresenter
import no.hiof.andersax.basket.presenter.UserPresenter
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class listOverviewFragment : Fragment() {
    private var presenter : ListPresenter = ListPresenter()
    private val uPresenter : UserPresenter = UserPresenter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.show()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView =  inflater.inflate(R.layout.fragment_list_overview, container, false)
        return myView


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getListOverViews(this)

        applyChanges.setOnClickListener {

            val addListAction =
                listOverviewFragmentDirections.actionListOverviewFragment2ToCreateListFragment()
            findNavController().navigate(addListAction)
        }
    }


    fun setUpListRecyclerView(list : ArrayList<ListCollection>){
        if(list.isNotEmpty()){
            singleListRecyclerView.adapter = ListOverviewAdapter(list,
                View.OnClickListener { view ->
                    val position = singleListRecyclerView.getChildAdapterPosition(view)
                    val clickedList = list[position]
                    //Action
                    val action =
                        listOverviewFragmentDirections.actionListOverviewFragment2ToPrivateListFragment(
                            clickedList.owner,
                            clickedList.description,
                            clickedList.listname,
                            clickedList.getUid()

                        );
                    findNavController().navigate(action)
                } ,
                View.OnLongClickListener {view ->
                    val position = singleListRecyclerView.getChildAdapterPosition(view)
                    val clickedList = list[position]
                    handleLongPress(clickedList, false)
                    true
                }
            )
            singleListRecyclerView.layoutManager = GridLayoutManager(context, 1)
        }else{
            noPrivateListsMessage.text = "You have no private lists yet, create one and lets get started"
        }
    }

    fun setUpSharedListRecyclerView(list : ArrayList<sharedList>) {

        if (list.isNotEmpty()) {
            noSharedListsMessage.text = ""
            sharedListRecyclerView.adapter = sharedListOverviewAdapter(list,
                View.OnClickListener { view ->
                    val position = sharedListRecyclerView.getChildAdapterPosition(view)
                    val clickedList = list[position]

                    println("- " + clickedList.listname + " - " + clickedList.owner)
                    val action =
                        listOverviewFragmentDirections.actionListOverviewFragment2ToSharedListFragment(
                            clickedList.getUid(),
                            clickedList.listname,
                            clickedList.description,
                            clickedList.owner,
                            clickedList.totalPrice,
                            clickedList.members.size.toLong()
                        )

                    findNavController().navigate(action)
                },
                View.OnLongClickListener { view ->
                    val position = sharedListRecyclerView.getChildAdapterPosition(view)
                    val clickedList = list[position]

                    handleLongPress(clickedList, true)
                    true
                }
            )
            sharedListRecyclerView.layoutManager = GridLayoutManager(context, 1)
        }else{
            noSharedListsMessage.text = "You have no shared lists yet, create one, invite your friends and lets get started"
        }
    }

    fun handleLongPress(list : ListCollection, isShared : Boolean){
        var build :  AlertDialog.Builder = AlertDialog.Builder(context)
        build.setCancelable(true)
        build.setTitle("Are you sure you want to delete this?")
        build.setNegativeButton("Cancel",  DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })
        build.setPositiveButton("Delete", DialogInterface.OnClickListener { dialogInterface, i ->
            if(!isShared) {
                uPresenter.handlePayMentForUser(list.getUid(), list.totalPrice, list.listname, Date(), "private")
            }
            presenter.handleListDelete(isShared, list.getUid(), this)
            presenter.getListOverViews(this)

        })
        build.show()
    }
    fun showToastToUser(message : String){
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(context, message, duration)

        toast.show()
    }




}










