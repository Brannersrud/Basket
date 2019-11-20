package no.hiof.andersax.basket

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.google.android.material.tabs.TabLayout
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_statistics.*
import no.hiof.andersax.basket.Adapter.ListHistoryAdapter
import no.hiof.andersax.basket.model.ListHistoryItem
import java.util.*
import kotlin.collections.ArrayList


class statisticsActivity() : AppCompatActivity() {
    //private lateinit var graph : GraphView
    private lateinit var items : ArrayList<ListHistoryItem>
    private val week : Long = 604800000L
    private val month : Long = 604800000L*4
    private lateinit var adapter : ListHistoryAdapter
    private lateinit var chartView : AnyChartView



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
                if(p0!!.position == 2 ){
                    //graph.series.clear()
                    showGraph()
                    usageLabel.text = "This months spending"
                    plotMonthData(items, chartView)
                }else if(p0!!.position == 1){
                    //graph.series.clear()
                    showGraph()
                    usageLabel.text = "This weeks spending"

                    plotWeekData(items, chartView)
                }else if(p0!!.position == 0){
                    ShowRecyclerView()
                    usageLabel.text ="History"
                    plotRecyclerView(items)
                }
            }
        })

    }

    private fun showGraph(){
        privateSpendingLabel.visibility = View.VISIBLE
        sharedSpendingLabel.visibility = View.VISIBLE
        chartView.visibility = View.VISIBLE
    }
    private fun ShowRecyclerView(){
        listHistoryRecyclerView.visibility = View.VISIBLE
    }

    private fun hideRecyclerView(){
        listHistoryRecyclerView.visibility = View.INVISIBLE
    }
    private fun hideGraph(){
        privateSpendingLabel.visibility = View.INVISIBLE
        sharedSpendingLabel.visibility = View.INVISIBLE
        chartView.visibility = View.INVISIBLE

    }
     fun plotRecyclerView(listhistory : MutableList<ListHistoryItem>){
            hideGraph()
            adapter = ListHistoryAdapter(listhistory, this)
            listHistoryRecyclerView.adapter = adapter
            listHistoryRecyclerView.layoutManager = GridLayoutManager(applicationContext, 1)

    }



    private fun plotMonthData(datalist : ArrayList<ListHistoryItem>, chartView: AnyChartView){
        hideRecyclerView()
        val date : Date = Date()
        val dateToMatch = (date.time - (month))
        val toDate = Date(dateToMatch)
        var privatePaid = 0.0
        var sharedPaid = 480.0

        for(item in datalist){
            if(item.date.after(toDate) && item.isPrivate.equals("private")){
                privatePaid+= item.pricepaid

            }else if(item.date.after(toDate) && !item.isPrivate.equals("private")){
                sharedPaid+=item.pricepaid
            }

        }
        val chart  = AnyChart.bar()

        val list : ArrayList<DataEntry> = ArrayList<DataEntry>()
        list.add(ValueDataEntry("private", privatePaid))
        list.add(ValueDataEntry("Shared", sharedPaid))

        chart.data(list)
        chartView.setChart(chart)


    }



    private fun plotWeekData(list : ArrayList<ListHistoryItem>, chartView: AnyChartView){
        hideRecyclerView()

        val date : Date = Date()
        val dateToMatch = (date.time - week)
        val toDate = Date(dateToMatch)
        var privatePaid = 0.0
        var sharedPaid = 480.0

        for(item in list){
            if(item.date.after(toDate) && item.isPrivate.equals("private")){
                privatePaid+= item.pricepaid

            }else if(item.date.after(toDate) && !item.isPrivate.equals("private")){
                sharedPaid+=item.pricepaid
            }

        }
        val chart  = AnyChart.bar()


        val list : ArrayList<DataEntry> = ArrayList<DataEntry>()
        list.add(ValueDataEntry("private", privatePaid))
        list.add(ValueDataEntry("Shared", sharedPaid))

        chart.data(list)
        chartView.setChart(chart)

    }




}


