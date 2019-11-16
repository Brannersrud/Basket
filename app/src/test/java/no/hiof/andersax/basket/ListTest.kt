package no.hiof.andersax.basket

import android.content.Context
import com.google.firebase.FirebaseApp
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import no.hiof.andersax.basket.model.ListItem
import no.hiof.andersax.basket.presenter.ListPresenter
import org.junit.Before
import org.junit.Test

class ListTest {
    private val presenter : ListPresenter = ListPresenter()



   /* @Test
    fun listCalculatesListElementsCorrectly(){
        val items : MutableList<ListItem> = ArrayList<ListItem>()

        items.add(ListItem("somelist", 4, false, 100))
        items.add(ListItem("somelist2", 4, false, 1000))
        items.add(ListItem("somelist", 4, false, 120))

        assertEquals(1220, presenter.calculateTotalPrice(items))
    }

    */
}