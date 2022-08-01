package com.example.finallydevice.AppusingStatics.AppusingPieChart.function

import android.graphics.Color
import android.util.Log
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class Drawpie(val pie:PieChart) {
    private var list: MutableList<PieEntry> = emptyList<PieEntry>().toMutableList()
    fun draw(){
        pie.setUsePercentValues(true)
        pie.setDrawEntryLabels(false)
        pie.description.isEnabled=false

        pie.legend.isWordWrapEnabled=true
        pie.legend.horizontalAlignment=Legend.LegendHorizontalAlignment.RIGHT
        pie.legend.verticalAlignment=Legend.LegendVerticalAlignment.TOP
        pie.setEntryLabelTextSize(20f)

        pie.holeRadius=50f
        pie.transparentCircleRadius=0f
        //list = listOf(PieEntry(30f,"1"), PieEntry(30f,"2"),PieEntry(60f,"3"))
        val dataset=PieDataSet(list,"")
        dataset.setColors(Color.BLUE,Color.GREEN,Color.RED,Color.LTGRAY,Color.MAGENTA,Color.CYAN)
        dataset.sliceSpace=3f
        //dataset.xValuePosition=PieDataSet.ValuePosition.OUTSIDE_SLICE
        //dataset.yValuePosition=PieDataSet.ValuePosition.INSIDE_SLICE
        val piedata=PieData(dataset)
        piedata.setValueFormatter(PercentFormatter(pie))
        piedata.setValueTextSize(15.0f)
        pie.data=piedata
        pie.invalidate()
    }
    fun setdata(datalist: List<Appusingdata_hour_database>){
        if(!list.isEmpty())
            list.clear()
        datalist.filter {
            it.totaltime>=1000
        }
        if(datalist.size>=5)
        {
            var sum=0L
            for(i in datalist.subList(0,5))
                list.add(PieEntry(i.totaltime.toFloat(),i.name))
            for(i in datalist.subList(5,datalist.size))
                sum+=i.totaltime
            list.add(PieEntry(sum.toFloat(),"其它"))
        }
        else
        {
            for(i in datalist)
                list.add(PieEntry(i.totaltime.toFloat(),i.name))
        }
    }
}