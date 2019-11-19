package no.hiof.andersax.basket

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Parcelable
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LabelFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.activity_statistics.*
import no.hiof.andersax.basket.model.ListHistoryItem
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.days


class statisticsActivity() : AppCompatActivity() {
    private lateinit var graph : GraphView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_statistics)

        val items  = intent.getParcelableArrayListExtra<Parcelable>("extra") as ArrayList<ListHistoryItem>

       graph = findViewById(R.id.graphViewBar) as GraphView



        plotWeekData(items, graph)





    }

    private fun plotWeekData(list : ArrayList<ListHistoryItem>, graph : GraphView){
        var series : BarGraphSeries<DataPoint> =  BarGraphSeries()
        val date : Date = Date()

       val dateToMatch = (date.time - 604800000L)

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