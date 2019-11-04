package no.hiof.andersax.basket

import android.content.Context
import com.google.firebase.FirebaseApp
import no.hiof.andersax.basket.view.loginFragment
import org.junit.Test

import org.junit.Assert.*
import com.google.firebase.auth.FirebaseAuth
import androidx.annotation.NonNull



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val logfrag : loginFragment = loginFragment()
    @Test
    fun log_in_denied_wrong_user() {
//        assertFalse(logfrag.doLogin("somefakelogin", "nonexistingpassword"))
    }
    @Test
    fun log_in_accepted_for_right_user(){

    }




}
