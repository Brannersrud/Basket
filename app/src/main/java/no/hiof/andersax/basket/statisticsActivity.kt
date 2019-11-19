package no.hiof.andersax.basket

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LabelFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.activity_statistics.*
import kotlinx.android.synthetic.main.activity_statistics.view.*
import no.hiof.andersax.basket.model.ListHistoryItem
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.days


class statisticsActivity() : AppCompatActivity() {
    private lateinit var graph : GraphView
    private lateinit var items : ArrayList<ListHistoryItem>
    private val week : Long = 604800000L
    private val month : Long = 604800000L*4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_statistics)
        items  = intent.getParcelableArrayListExtra<Parcelable>("extra") as ArrayList<ListHistoryItem>

       graph = findViewById(R.id.graphViewBar) as GraphView

        plotWeekData(items, graph)

        tablayout_statistics.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
                println("reselected")
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                println("unselected")
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {

                if(p0!!.position == 1 ){
                    graph.series.clear()
                    usageLabel.text = "This months spending"
                    plotMonthData(items, graph)
                }else if(p0!!.position == 0){
                    graph.series.clear()
                    usageLabel.text = "This weeks spending"
                    plotWeekData(items, graph)
                }
            }

        })



    }



    private fun plotMonthData(list : ArrayList<ListHistoryItem>, graph: GraphView){
        var series : BarGraphSeries<DataPoint> =  BarGraphSeries()
        val date : Date = Date()

        val dateToMatch = (date.time - (month))

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
        series.appendData(DataPoint(2.0, privatePaid), true, 1000)
        series.appendData(DataPoint(3.0, sharedPaid), true,1000)


        series.spacing = 10
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.RED
        series.setValueDependentColor { it ->
            Color.rgb(Math.random().toInt(), (it.x * 255/4).toInt(), Math.abs(it.y * 255/4).toInt())
        }
        graph.addSeries(series)


    }

    private fun plotWeekData(list : ArrayList<ListHistoryItem>, graph : GraphView){
        var series : BarGraphSeries<DataPoint> =  BarGraphSeries()
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
        series.appendData(DataPoint(2.0, privatePaid), true, 1000)
        series.appendData(DataPoint(3.0, sharedPaid), true,1000)


        series.spacing = 10
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.RED
        series.setValueDependentColor { it ->
            Color.rgb(Math.random().toInt(), (it.x * 255/4).toInt(), Math.abs(it.y * 255/4).toInt())
        }

        graph.addSeries(series)




    }




}


