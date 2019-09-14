package no.hiof.andersax.basket.model

import no.hiof.andersax.basket.presenter.ListPresenter

abstract class List(val listname : String, val description : String, val ListItems : ArrayList<ListItem>, val members : ArrayList<User>, val Owner : String) {
    abstract val presenter : ListPresenter


    fun calculateTotalPrice(list : ArrayList<ListItem>) : Int{
        var temp : Int = 0;
        for (i in list){
            if(i.isChecked){
                temp+=i.price;
            }
        }
        return temp;
    }


    fun addListItemToList(listItem : ListItem){
        this.ListItems.add(listItem)
    }
    fun removeListItem(listItem : ListItem){
        this.ListItems.remove(listItem)

    }

    fun addMemberToList(user: User){
        this.members.add(user)
    }



}