package no.hiof.andersax.basket.Adapter

import android.app.LauncherActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_private_list.view.*
import kotlinx.android.synthetic.main.list_items_view.view.*
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListItem

class listItemAdapter(private val listitems : MutableList<ListItem>, var clickListener: View.OnClickListener) : RecyclerView.Adapter<listItemAdapter.ListItemHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): listItemAdapter.ListItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_items_view, parent, false)

        return ListItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listitems.size
    }

    override fun onBindViewHolder(holder: listItemAdapter.ListItemHolder, position: Int) {
        val currentList = listitems[position]

        holder.bind(currentList, clickListener)
    }
    class ListItemHolder(view : View) : RecyclerView.ViewHolder(view){
            private val listName : TextView = view.itemName
            private val numberOfItems : TextView = view.numberOfItems
            private val checked : CheckBox = view.checkBoxListItem

        fun bind(item : ListItem, clickListener: View.OnClickListener){
                listName.text = item.itemName
                numberOfItems.text = item.quantity.toString()
                checked.isChecked = item.isChecked

            this.itemView.setOnClickListener(clickListener)


        }
    }

}