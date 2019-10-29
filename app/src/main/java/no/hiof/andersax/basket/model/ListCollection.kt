package no.hiof.andersax.basket.model

import kotlin.collections.List

open class ListCollection( val listname : String, val description : String, val Owner : String, val items : MutableList<ListItem>, var totalPrice : Long) {
    private var uid : String = "";


    fun getListItems() : List<ListItem>{
        return this.items
    }
    fun setUid(uid : String){
        this.uid = uid;
    }

    fun getUid() : String {
        return this.uid;
    }

}