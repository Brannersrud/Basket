package no.hiof.andersax.basket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Parcelable






class statisticsActivity() : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = intent.getParcelableArrayListExtra<Parcelable>("extra")
        println(items.toString())
    }

    //make piechart? or bar?
    //or bar graph?
    //or both :O wuut



    //add backswipe?
    //

}