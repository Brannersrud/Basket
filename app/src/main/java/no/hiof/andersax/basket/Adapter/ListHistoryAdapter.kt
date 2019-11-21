package no.hiof.andersax.basket.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_item.view.*
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListHistoryItem
import no.hiof.andersax.basket.profileActivity
import no.hiof.andersax.basket.statisticsActivity

class ListHistoryAdapter(private val listHistory : MutableList<ListHistoryItem>, var activity : statisticsActivity) : RecyclerView.Adapter<ListHistoryAdapter.ListHistoryHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListHistoryAdapter.ListHistoryHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history_item,parent, false)


        return ListHistoryHolder(itemView, activity)

    }
    fun getListHistory() : MutableList<ListHistoryItem>{
        return listHistory
    }

    override fun getItemCount(): Int {
        return listHistory.size

    }


    override fun onBindViewHolder(holder: ListHistoryAdapter.ListHistoryHolder, position: Int) {
        val currentList = listHistory[position]

        holder.bind(currentList)

    }

    class ListHistoryHolder(view : View, activity : statisticsActivity) : RecyclerView.ViewHolder(view){
        private val pricepaid : TextView = view.pricePaidLabelHistory
        private val itemTitle : TextView = view.listHistoryListTitle
        private val date : TextView = view.datePaid
        private var act = activity


        fun bind(item : ListHistoryItem){
            pricepaid.text = item.pricepaid.toString()
            itemTitle.text = item.listName
            date.text = item.date.toLocaleString()

        }

    }
}