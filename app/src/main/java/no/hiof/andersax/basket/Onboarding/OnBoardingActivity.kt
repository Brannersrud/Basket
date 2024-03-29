package no.hiof.andersax.basket.Onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import no.hiof.andersax.basket.MainActivity
import no.hiof.andersax.basket.R
import no.hiof.andersax.basket.services.SharedPreferencesGateWay

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var viewpager : ViewPager
    private lateinit var tabindicatior : TabLayout
    private lateinit var btnNext : Button
    private var position : Int = 0
    private var prefs : SharedPreferencesGateWay = SharedPreferencesGateWay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introactivity)

        viewpager = findViewById(R.id.onboardingPager)
        tabindicatior = findViewById(R.id.tabindicator_boarding)
        btnNext = findViewById(R.id.onBoardingNext)

        val adapter = introViewPagerAdapter(this, getScreenData())

        viewpager.adapter = adapter

        tabindicatior.setupWithViewPager(viewpager)

        btnNext.setOnClickListener {
            changeScreen()

        }

    }

    private fun changeScreen() {
        position = viewpager.currentItem

        if (position < getScreenData().size) {
            position++
            viewpager.currentItem = position
        }
        if (position == getScreenData().size) {
            savePrefsData()
        }
    }

    private fun getScreenData() : MutableList<ScreenItem>{
        val screenList : MutableList<ScreenItem> = ArrayList()

        screenList.add(ScreenItem("Organize your shopping", "Organize your lists to help you remember what to buy", R.drawable.shopping))
        screenList.add(ScreenItem("Keep it private", "Create private lists that are only for your eyes", R.drawable.safetylist))
        screenList.add(ScreenItem("Share with friends", "Create lists with your friend(s) for your events and gatherings", R.drawable.shared))

        return screenList
    }

    private fun goToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun savePrefsData(){
        prefs.savePrefsData(applicationContext, "myPrefs", "isIntroFinished")

        goToMainActivity()
    }


}