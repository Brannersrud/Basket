package no.hiof.andersax.basket.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_search_layout.view.*
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListMembers
import no.hiof.andersax.basket.view.createListFragment

class SearchAdapter(private val searchItems : MutableList<ListMembers>, var clickListener: View.OnClickListener): RecyclerView.Adapter<SearchAdapter.SearchItemHolder>(){
    override fun onBindViewHolder(holder: SearchItemHolder, position: Int) {
        val currentList = searchItems[position]

        holder.bind(currentList,clickListener)

    }

    override fun getItemCount(): Int {
       return searchItems.size;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemHolder {
        val itemView  = LayoutInflater.from(parent.context).inflate(R.layout.user_search_layout, parent, false)

        return SearchItemHolder(itemView);
    }

    class SearchItemHolder(view : View) : RecyclerView.ViewHolder(view){
        private val listholder : createListFragment = createListFragment()
        private val email : TextView = view.emailFieldSearchResult;


        fun bind(item: ListMembers, clickListener: View.OnClickListener){
            email.text = item.username


            this.itemView.setOnClickListener(clickListener)


        }




    }


}