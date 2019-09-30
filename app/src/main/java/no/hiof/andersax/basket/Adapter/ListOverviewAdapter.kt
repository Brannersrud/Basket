package no.hiof.andersax.basket.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListCollection

class ListOverviewAdapter(private val items:List<ListCollection>, var clickListener: View.OnClickListener) : RecyclerView.Adapter<ListOverviewAdapter.ListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOverviewAdapter.ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListOverviewAdapter.ListViewHolder, position: Int) {

        val currentList = items[position]

        holder.bind(currentList, clickListener)
    }

    class ListViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val itemName : TextView = view.cardViewTitle
        private val description : TextView = view.cardViewDescription


        fun bind(item : ListCollection, clickListener: View.OnClickListener){
            itemName.text = item.listname
            description.text = item.description

            this.itemView.setOnClickListener(clickListener)


        }
    }


}