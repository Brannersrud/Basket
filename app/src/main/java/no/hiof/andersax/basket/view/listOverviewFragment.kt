package no.hiof.andersax.basket.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_list_overview.*
import no.hiof.andersax.basket.Adapter.ListOverviewAdapter
import no.hiof.andersax.basket.Adapter.sharedListOverviewAdapter
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.sharedList
import no.hiof.andersax.basket.presenter.ListPresenter


/**
 * A simple [Fragment] subclass.
 */
class listOverviewFragment : Fragment() {
    private var Auth : AuthActions = AuthActions()
    private var presenter : ListPresenter = ListPresenter()
    private val shared : sharedListFragment = sharedListFragment()





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
                }
            )
            singleListRecyclerView.layoutManager = GridLayoutManager(context, 1)
        }

    fun setUpSharedListRecyclerView(list : ArrayList<sharedList>){
        sharedListRecyclerView.adapter = sharedListOverviewAdapter(list,
            View.OnClickListener {view ->
                val position = sharedListRecyclerView.getChildAdapterPosition(view)
                val clickedList = list[position]

                println("- " + clickedList.listname + " - " + clickedList.owner)
               val action = listOverviewFragmentDirections.actionListOverviewFragment2ToSharedListFragment(clickedList.getUid(), clickedList.listname, clickedList.description, clickedList.owner, clickedList.totalPrice, clickedList.members.size.toLong())

                findNavController().navigate(action)
            })
        sharedListRecyclerView.layoutManager = GridLayoutManager(context,1)
    }



}










