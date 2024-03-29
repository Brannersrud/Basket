package no.hiof.andersax.basket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.history_item.*
import no.hiof.andersax.basket.Adapter.ListHistoryAdapter
import no.hiof.andersax.basket.model.ListHistoryItem
import no.hiof.andersax.basket.presenter.ListPresenter
import no.hiof.andersax.basket.presenter.UserPresenter

class profileActivity: AppCompatActivity() {
    private var userPresenter : UserPresenter = UserPresenter()
    private var username : String = ""
    private lateinit var userHistory : MutableList<ListHistoryItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        userPresenter.getProfileInformation(this)
        //call a method in db that gets the listcount, name from db
        //let it set up by passing the activity to the method

        //create a button that launches the intent
            textHistLab.setOnClickListener {
                onUserRedirectToStatistic()
            }
    }


    private fun onUserRedirectToStatistic(){
        val arr = ArrayList(userHistory)
        val intent = Intent(applicationContext, statisticsActivity::class.java)
        intent.putParcelableArrayListExtra("extra", arr)
        startActivity(intent)
    }

    fun setUpPrivateCount(privateCount: Int) {
        privateListProfileCount.text = privateCount.toString()
    }

    fun setUpSharedCount(sharedcount: Int){
    sharedListCountProfile.text = sharedcount.toString()
}

    fun setUpName(name : String){
        username=name
        profileNameText.text = username
        prepareHistory(name)
    }
    private fun prepareHistory(name : String){
        userPresenter.getHistoryForUser(this, name)
    }

    fun setHistoryForUser(listhistory : MutableList<ListHistoryItem>){
        var sortedList = listhistory.sortedWith(compareBy({it.date}))
        if(sortedList.isNotEmpty()) {
            this.userHistory = sortedList as MutableList<ListHistoryItem>
        }else{
            this.userHistory = listhistory
        }
    }

}