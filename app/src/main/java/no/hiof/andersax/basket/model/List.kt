package no.hiof.andersax.basket.model

import no.hiof.andersax.basket.IuserList
import no.hiof.andersax.basket.presenter.ListPresenter

open class List(val listname : String, val description : String, val Owner : String, val items : ArrayList<ListItem>) {


    fun calculateTotalPrice(list: ArrayList<ListItem>): Int {
        var temp: Int = 0;
        for (i in list) {
            if (i.isChecked) {
                temp += i.price;
            }
        }
        return temp;
    }

    fun addItemToList(item : ListItem){
        items.add(item)
    }
    fun getListItems() : ArrayList<ListItem>{
        return this.items
    }



}