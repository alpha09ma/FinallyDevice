package com.example.finallydevice.ScreenStatics.screenbar.function

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import com.example.finallydevice.ScreenStatics.screenbar.dataclass.ScreenStatics
import com.example.finallydevice.repositry.ScreenusingRepository
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.time.LocalDate

class DrawBarDouble(val context: Context) {
    private lateinit var type: String
    private var datelist:MutableList<ScreenStatics> = mutableListOf()
    private val datalist: MutableList<BarEntry> = emptyList<BarEntry>().toMutableList()
    fun draw(bar: BarChart){
        bar.setDrawBarShadow(false)
        //bar.setDrawValueAboveBar(true)
        bar.setMaxVisibleValueCount(7)
        bar.setScaleEnabled(false)
        bar.setPinchZoom(false)
        bar.setDrawGridBackground(false)
        bar.description.isEnabled=false
        bar.setScaleEnabled(false)

        val xAxis: XAxis = bar.getXAxis()
        xAxis.axisMinimum=0f
        xAxis.axisMaximum=8f
        xAxis.setGranularity(1f)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setDrawGridLines(false)
        xAxis.setLabelCount(7)
        xAxis.spaceMax=2f
        xAxis.spaceMin=1f
        val xlist:List<String> = listOf("周一","周二","周三","周四","周五","周六","周日")
        xAxis.valueFormatter = object : ValueFormatter(){
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                if(value<=7f&&value>=1f)
                    return xlist[(value-1).toInt()]
                return ""
            }
        }
        val leftAxis: YAxis = bar.getAxisLeft();
        leftAxis.axisMinimum=0f
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(12f);
        leftAxis.setTextColor(Color.BLUE);

        leftAxis.isEnabled=false

        val rightAxis = bar.getAxisRight();
        rightAxis.isEnabled=false

        val mLegend = bar.getLegend();
        mLegend.isEnabled=false

        val dataset = BarDataSet(datalist,"")
        dataset.setColor(Color.GRAY)

        bar.data= BarData(dataset)
        bar.setOnChartValueSelectedListener(
            object :OnChartValueSelectedListener{
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    val x=datelist.find {
                        it.date.dayOfWeek.value==e?.x!!.toInt()
                    }
                    Log.d("test",e.toString())
                    if(e?.y!!.toInt()!=0)
                    {
                        if(type!="count")
                            Toast.makeText(context,com.example.finallydevice.share.TypeConverter.datetotext(x!!.date)+
                                    com.example.finallydevice.share.TypeConverter.timetotext(e?.y!!.toLong()),
                                Toast.LENGTH_LONG).show()
                        else
                        {
                            Toast.makeText(context,com.example.finallydevice.share.TypeConverter.datetotext(x!!.date)+e?.y!!.toInt()+"次",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }
                override fun onNothingSelected() {
                }
            }
        )
        bar.invalidate()
    }
    fun setdata(_list:List<ScreenStatics>, type:String){
        var list=_list.toMutableList()
        val repository:ScreenusingRepository= ScreenusingRepository(context)
        if(!datalist.isEmpty())
            datalist.clear()
        list.add(ScreenStatics(repository.getstatics(type), LocalDate.now()))
        if(type!="count")
            list= list.filter {
            it.total>=1000
            } as MutableList<ScreenStatics>
        if(list.size!=0)
            when(list[0].date.dayOfWeek.name)
            {
            "TUESDAY"->datalistadd(1)
            "WEDNESDAY"->datalistadd(2)
            "THURSDAY"->datalistadd(3)
            "FRIDAY"->datalistadd(4)
            "SATURDAY"->datalistadd(5)
            "SUNDAY"->datalistadd(6)
            }
        list.forEach {
            datalist.add(BarEntry(datalist.size.toFloat()+1,it.total.toFloat()))
        }
        while (datalist.size<7)
            datalist.add(BarEntry(datalist.size.toFloat()+1,0f))
        this.type=type
        datelist=list
        Log.d("iu",datelist.toString())
        Log.d("iu",datalist.toString())
    }
    private fun datalistadd(n:Int){
        for(i in 1..n)
            datalist.add(BarEntry(i.toFloat(), 0f))
    }
}

