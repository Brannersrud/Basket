package no.hiof.andersax.basket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.firebase.FirebaseApp
import no.hiof.andersax.basket.Database.AuthActions

class MainActivity : AppCompatActivity() {
    private val Auth : AuthActions  = AuthActions()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        setSupportActionBar(findViewById(R.id.mytoolbar))
        setTitle("")




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id : Int = item.itemId

        if(id == R.id.action_favorite && Auth.getCurrentUser().email != null){
            val myIntent = Intent(this, profileActivity::class.java)
            startActivity(myIntent)

            return true
        }else{
            Toast.makeText(this, "You need to log in first", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
