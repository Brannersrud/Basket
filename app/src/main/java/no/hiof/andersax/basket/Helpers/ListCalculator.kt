package no.hiof.andersax.basket.Helpers

import no.hiof.andersax.basket.model.ListItem

class ListCalculator{



    fun calculateTotal(list : MutableList<ListItem>):Long{
        var temp: Long = 0
        for (i in list) {
            if (i.isChecked && i.price > 0) {
                temp += i.price
            }
        }
        return temp
    }
}