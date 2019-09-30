package no.hiof.andersax.basket.view


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_list_overview.*
import no.hiof.andersax.basket.Adapter.ListOverviewAdapter
import no.hiof.andersax.basket.Database.ListActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter

/**
 * A simple [Fragment] subclass.
 */
class listOverviewFragment : Fragment() {
    private val listp : ListPresenter = ListPresenter()
    private val act : ListActions = ListActions()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        act.getPrivateLists(listp)

        // Inflate the layout for this fragment
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
        setUpListRecyclerView()
    }

    fun setUpListRecyclerView(){
        Handler().postDelayed({
            val list : ArrayList<ListCollection> = listp.getPrivateLists()
            privateListRecyclerView.adapter = ListOverviewAdapter(list,
                View.OnClickListener { view  ->
                    val position = privateListRecyclerView.getChildAdapterPosition(view)
                    val clickedList = list[position]
                    //Action

                    Toast.makeText(view.context, clickedList.listname, Toast.LENGTH_SHORT).show()
                }
            )
            privateListRecyclerView.layoutManager = GridLayoutManager(context, 1)

        },1000)

    }
}
