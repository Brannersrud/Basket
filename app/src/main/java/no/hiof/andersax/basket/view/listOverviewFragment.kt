package no.hiof.andersax.basket.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_list_overview.*
import no.hiof.andersax.basket.R

/**
 * A simple [Fragment] subclass.
 */
class listOverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }
}
