package no.hiof.andersax.basket

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.graphics.vector.SolidFill
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_statistics.*
import no.hiof.andersax.basket.Adapter.ListHistoryAdapter
import no.hiof.andersax.basket.model.ListHistoryItem
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class statisticsActivity() : AppCompatActivity() {
    //private lateinit var graph : GraphView
    private lateinit var items : ArrayList<ListHistoryItem>
    private val week : Long = 604800000L
    private val month : Long = 604800000L*4
    private lateinit var adapter : ListHistoryAdapter
    private lateinit var chartView : AnyChartView
    private var list : ArrayList<DataEntry> = ArrayList<DataEntry>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        items  = intent.getParcelableArrayListExtra<Parcelable>("extra") as ArrayList<ListHistoryItem>
        //graph = findViewById(R.id.graphViewBar) as GraphView
        chartView = findViewById(R.id.chart_view)


        plotRecyclerView(items)

        tablayout_statistics.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
                println("reselected")
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                println("unselected")
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if(p0!!.position == 1 ){
                    showGraph()
                    usageLabel.text = "Past month spending"
                    plotMonthData(items)
                }/*else if(p0!!.position == 1){
                    showGraph()
                    usageLabel.text = "Past week spending"
                    plotWeekData(items)
                }*/else if(p0!!.position == 0){
                    ShowRecyclerView()
                    usageLabel.text ="History"
                    plotRecyclerView(items)
                }
            }
        })

    }

    private fun showGraph(){
        chartView.visibility = View.VISIBLE
    }
    private fun ShowRecyclerView(){
        listHistoryRecyclerView.visibility = View.VISIBLE
    }

    private fun hideRecyclerView(){
        listHistoryRecyclerView.visibility = View.INVISIBLE
    }
    private fun hideGraph(){
        chartView.visibility = View.INVISIBLE

    }
     fun plotRecyclerView(listhistory : MutableList<ListHistoryItem>){
            hideGraph()
            adapter = ListHistoryAdapter(listhistory, this)
            listHistoryRecyclerView.adapter = adapter
            listHistoryRecyclerView.layoutManager = GridLayoutManager(applicationContext, 1)

    }



    private fun plotMonthData(datalist : ArrayList<ListHistoryItem>){
        hideRecyclerView()
        val date : Date = Date()
        val dateToMatch = (date.time - month)
        val toDate = Date(dateToMatch)
        var privatePaid = 0.0
        var sharedPaid = 0.0
            for (item in datalist) {
                if (item.date.after(toDate) && item.isPrivate.equals("private")) {
                    privatePaid += item.pricepaid
                } else if (item.date.after(toDate) && item.isPrivate.equals("shared")) {
                    sharedPaid += item.pricepaid
                }

            }

            val chart  = AnyChart.column()

            list.add(ValueDataEntry("private", privatePaid))
            list.add(ValueDataEntry("Shared", sharedPaid))

            chart.data(list)

            this.chartView.setChart(chart)


        chart.palette().itemAt(0, SolidFill("#0100CA", 1.0))


    }


/*
    private fun plotWeekData(listhist : ArrayList<ListHistoryItem>){
        hideRecyclerView()
        val date : Date = Date()
        val dateToMatch = (date.time - week)
        val toDate = Date(dateToMatch)
        var privatePaid = 0.0
        var sharedPaid = 0.0

        for(item in listhist){
            if(item.date.after(toDate) && item.isPrivate.equals("private")){
                privatePaid+=item.pricepaid

            }else if(item.date.after(toDate) && item.isPrivate.equals("shared")){
                sharedPaid+=item.pricepaid
            }

        }
        val chart  = AnyChart.column()

        this.list.add(ValueDataEntry("private", privatePaid))
        this.list.add(ValueDataEntry("Shared", sharedPaid))

        chart.data(list)

        this.chartView.setChart(chart)

    }


*/

}


