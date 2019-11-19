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
import no.hiof.andersax.basket.view.listOverviewFragment

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var viewpager : ViewPager
    private lateinit var tabindicatior : TabLayout
    private lateinit var btnNext : Button
    private var position : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_introactivity)


        if(restoredPrefData()){
        goToMainActivity()
        }
        viewpager = findViewById(R.id.onboardingPager)
        tabindicatior = findViewById(R.id.tabindicator_boarding)
        btnNext = findViewById(R.id.onBoardingNext)

        val screenList : MutableList<ScreenItem> = ArrayList()

        screenList.add(ScreenItem("Private", "Create private lists to help you remember what to buy", R.drawable.account_icon))
        screenList.add(ScreenItem("Shared", "Create lists with your friend for your events and gatherings", R.drawable.add_friend))
        screenList.add(ScreenItem("Third page", "this is the third", R.drawable.avatar))

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
        val preferences : SharedPreferences = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val prefEd : SharedPreferences.Editor = preferences.edit()

        prefEd.putBoolean("isIntroFinished", true)
        prefEd.commit()

        goToMainActivity()
    }

    fun restoredPrefData() : Boolean{
        val pref : SharedPreferences = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val isIntroFinished = pref.getBoolean("isIntroFinished", false)

        return isIntroFinished
    }
}