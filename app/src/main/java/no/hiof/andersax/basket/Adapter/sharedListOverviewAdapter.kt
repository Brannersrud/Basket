package no.hiof.andersax.basket.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import no.hiof.andersax.basket.Database.AuthActions
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.sharedList

class sharedListOverviewAdapter(private val items:ArrayList<sharedList>, var clickListener: View.OnClickListener) : RecyclerView.Adapter<sharedListOverviewAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sharedListOverviewAdapter.ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: sharedListOverviewAdapter.ListViewHolder, position: Int) {

        val currentList = items[position]

        holder.bind(currentList, clickListener)
    }

    class ListViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val itemName : TextView = view.cardViewTitle
        private val description : TextView = view.cardViewDescription
        private val ownerName : TextView = view.ownerNameCardTitle
        private val amountOfMembers : TextView = view.amountOfMembers
        private val Auth : AuthActions = AuthActions()
        private val members : ImageView = view.membersIcon


        fun bind(item : sharedList, clickListener: View.OnClickListener){
            itemName.text = item.listname
            description.text = item.description
            amountOfMembers.text = item.members.size.toString()
            members.setImageResource(R.drawable.add_friend)
            if(item.owner.equals(this.Auth.getCurrentUser().email)){
                ownerName.text = "owner: you:)"
            }else{
                ownerName.text = "owner: " + item.owner
            }

            this.itemView.setOnClickListener(clickListener)


        }
    }

}