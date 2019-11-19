package no.hiof.andersax.basket.model

import kotlin.collections.List

open class ListCollection( val listname : String, val description : String, val owner : String, val items : MutableList<ListItem>, var totalPrice : Long, var ListItems : ArrayList<ListItem>) {
    private var uid : String = "";

    constructor() : this(listname ="", description = "", owner = "", items = ArrayList<ListItem>(), totalPrice = 0, ListItems = ArrayList<ListItem>())




    fun setUid(uid : String){
        this.uid = uid;
    }

    fun getUid() : String {
        return this.uid;
    }

}