package no.hiof.andersax.basket.model

class ListItem(var itemName: String, var quantity: Long, var isChecked: Boolean, var price: Long) {

constructor():this("", 0 , false, 0)

}