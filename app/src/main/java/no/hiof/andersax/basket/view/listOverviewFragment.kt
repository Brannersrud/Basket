package no.hiof.andersax.basket.view


import android.os.Bundle
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
import no.hiof.andersax.basket.model.List
import no.hiof.andersax.basket.model.ListItem

/**
 * A simple [Fragment] subclass.
 */
class listOverviewFragment : Fragment() {
    private val actl : ListActions = ListActions()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_list_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actl.getPrivateLists()

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
        val list : ArrayList<List> = ArrayList()
        val items : ArrayList<ListItem> = ArrayList()
        items.add(ListItem("something", 2, false, 200))
        list.add(List("some name", "description", "you", items))
                list.add(List("soe", "description", "you", items))
        list.add(List("some nme", "description", "you", items))
        list.add(List("soame", "description", "you", items))
        list.add(List("some", "description", "you", items))

        privateListRecyclerView.adapter = ListOverviewAdapter(list,
            View.OnClickListener { view  ->
                val position = privateListRecyclerView.getChildAdapterPosition(view)
                val clickedList = list[position]
                //Action

                Toast.makeText(view.context, clickedList.listname, Toast.LENGTH_SHORT).show()
            }
        )
        privateListRecyclerView.layoutManager = GridLayoutManager(context, 1)
    }
}
