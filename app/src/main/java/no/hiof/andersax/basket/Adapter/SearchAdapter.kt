package no.hiof.andersax.basket.Adapter

import android.app.LauncherActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_search_layout.view.*
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.model.User

class SearchAdapter(private val searchItems : ArrayList<User>, var clickListener: View.OnClickListener): RecyclerView.Adapter<SearchAdapter.SearchItemHolder>(){
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
        private val email : TextView = view.emailFieldSearchResult;
        private val phone : TextView = view.phoneLabelAddUser;
        private val checked : RadioButton = view.radioButtonAddFriends;


        fun bind(item : User, clickListener: View.OnClickListener){
            email.text = item.email
            phone.text = item.phone
            checked.isChecked = false

            this.itemView.setOnClickListener(clickListener)


        }
    }

}