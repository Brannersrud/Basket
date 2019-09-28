package no.hiof.andersax.basket.Adapter

import android.app.LauncherActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.model.ListItem

class listItemAdapter(private val listitems : ArrayList<LauncherActivity.ListItem>, var clickListener: View.OnClickListener) : RecyclerView.Adapter<listItemAdapter.ListItemHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): listItemAdapter.ListItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_items_view, parent, false)

        return ListItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: listItemAdapter.ListItemHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    class ListItemHolder(view : View) : RecyclerView.ViewHolder(view){



        fun bind(item : ListItem, clickListener: View.OnClickListener){


            this.itemView.setOnClickListener(clickListener)


        }
    }

}