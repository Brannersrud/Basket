package no.hiof.andersax.basket.Onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import no.hiof.andersax.basket.MainActivity
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.services.sharedPreferencesManipulator
import no.hiof.andersax.basket.view.listOverviewFragment

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var viewpager : ViewPager
    private lateinit var tabindicatior : TabLayout
    private lateinit var btnNext : Button
    private var position : Int = 0
    private var prefs : sharedPreferencesManipulator = sharedPreferencesManipulator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_introactivity)


        if(prefs.restoredPrefData(applicationContext, "myPrefs", "isIntroFinished")){
        goToMainActivity()
        }
        viewpager = findViewById(R.id.onboardingPager)
        tabindicatior = findViewById(R.id.tabindicator_boarding)
        btnNext = findViewById(R.id.onBoardingNext)

        val screenList : MutableList<ScreenItem> = ArrayList()

        screenList.add(ScreenItem("Organize your shopping", "Organize your lists to help you remember what to buy", R.drawable.shopping))
        screenList.add(ScreenItem("Keep it private", "Create private lists that are only for your eyes", R.drawable.safetylist))
        screenList.add(ScreenItem("Share with friends", "Create lists with your friend(s) for your events and gatherings", R.drawable.shared))

        val adapter : introViewPagerAdapter = introViewPagerAdapter(this, screenList)

        viewpager.adapter = adapter

        tabindicatior.setupWithViewPager(viewpager)

        btnNext.setOnClickListener {
            position = viewpager.currentItem

            if(position < screenList.size){
                position++
                viewpager.currentItem = position
            }
            if(position == screenList.size){
                savePrefsData()
            }

        }

    }

    fun goToMainActivity(){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun savePrefsData(){
        prefs.savePrefsData(applicationContext, "myPrefs", "isIntroFinished")

        goToMainActivity()
    }


}