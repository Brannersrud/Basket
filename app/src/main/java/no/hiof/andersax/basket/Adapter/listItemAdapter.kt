package no.hiof.andersax.basket.Adapter

import android.app.LauncherActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_private_list.view.*
import kotlinx.android.synthetic.main.list_items_view.view.*
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListItem

class listItemAdapter(private val listitems : MutableList<ListItem>) : RecyclerView.Adapter<listItemAdapter.ListItemHolder>() {
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

        holder.bind(currentList)
    }
    class ListItemHolder(view : View) : RecyclerView.ViewHolder(view){
            private var listName : TextView = view.itemName
            private var numberOfItems : TextView = view.numberOfItems
            private var checked : CheckBox = view.checkBoxListItem

        fun bind(item : ListItem){
                listName.text = item.itemName
                numberOfItems.text = item.quantity.toString()
                checked.isChecked = item.isChecked

            checked.setOnClickListener {
                checkMakeItemChecked(item, checked.isChecked)
                //item.isChecked = checked.isChecked
            }
            listName.addTextChangedListener {
                changeNameOfListItem(item, listName.text.toString())
                //item.itemName = listName.text.toString()
            }
            numberOfItems.addTextChangedListener {
                changeQuantityOfItem(item, numberOfItems.text.toString().toLong())
                //item.quantity = numberOfItems.text.toString().toLong()
            }

        }

        //move this maybe ? could me smarter to have it in a different component, alltho easier i guess

        fun changeNameOfListItem(item : ListItem, newName : String){
            item.itemName = newName
        }
        fun checkMakeItemChecked(item : ListItem, isChecked : Boolean){
            item.isChecked = isChecked
        }
        fun changeQuantityOfItem(item : ListItem, quantity : Long){
            item.quantity = quantity
        }

    }





}