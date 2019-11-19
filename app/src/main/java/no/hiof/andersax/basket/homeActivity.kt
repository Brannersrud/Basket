package no.hiof.andersax.basket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class homeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // supportActionBar!!.hide()

        setContentView(R.layout.activity_home)
    }
}