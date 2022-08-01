package com.example.finallydevice.AppusingStatics.Appusing.function

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import com.example.finallydevice.share.Service_function.AppUsingTime
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_day_database
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Drawbar(val context: Context,val appinfo:Appusingdata_hour_database) {
    private val datalist: MutableList<BarEntry> = emptyList<BarEntry>().toMutableList()
    private lateinit var datelist_day:List<Appusingdata_day_database>
    private lateinit var datelist_hour:List<Appusingdata_hour_database>
    fun draw(bar:BarChart,count:Int){
        bar.setDrawBarShadow(false)
        //bar.setDrawValueAboveBar(true)
        bar.setMaxVisibleValueCount(count)
        bar.setPinchZoom(false)
        bar.setDrawGridBackground(false)
        bar.description.isEnabled=false
        bar.setScaleEnabled(false)

        val xAxis:XAxis= bar.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setDrawGridLines(false)
        xAxis.spaceMax=2f
        xAxis.spaceMin=1f
        if(count==7)
        {
            xAxis.setLabelCount(count)
            val xlist:List<String> = listOf("周一","周二","周三","周四","周五","周六","周日")
            xAxis.valueFormatter = object : ValueFormatter(){
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    if(value<=7f&&value>=1f)
                        return xlist[(value-1).toInt()]
                    return ""
                }
            }
        }
        if(count==24)
        {
            xAxis.setLabelCount(count)
            val xlist:List<String> = listOf("00:00","06:00","12:00","18:00","23:00")
            xAxis.valueFormatter = object : ValueFormatter(){
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    when(value)
                    {
                        1f->return xlist[0]
                        7f->return xlist[1]
                        13f->return xlist[2]
                        19f->return xlist[3]
                        24f->return xlist[4]
                    }
                    return ""
                }
            }
        }
        val leftAxis: YAxis = bar.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(12f);
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.axisMinimum=0f
        leftAxis.isEnabled=false

        val rightAxis = bar.getAxisRight();
        rightAxis.isEnabled=false

        val mLegend = bar.getLegend();
        mLegend.isEnabled=false

        Log.d("test",datalist.size.toString()+" "+count.toString())
        val dataset = BarDataSet(datalist,"")
        dataset.setColor(Color.GRAY)

        bar.data= BarData(dataset)
        bar.setOnChartValueSelectedListener(
            object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    if(count==7)
                    {
                        val x=datelist_day.find {
                            it.date.dayOfWeek.value==e?.x!!.toInt()
                        }
                        Log.d("test",x.toString())
                        if(e?.y!!.toInt()!=0)
                        //Log.d("test",datalist.toString())
                        Toast.makeText(context,com.example.finallydevice.share.TypeConverter.datetotext(x!!.date)+
                                com.example.finallydevice.share.TypeConverter.timetotext(e?.y!!.toLong()),
                            Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        val x=datelist_hour.find {
                            it.starttime==e?.x!!.toInt()-1
                        }
                        if(e?.y!!.toInt()!=0)
                        Toast.makeText(context,com.example.finallydevice.share.TypeConverter.hourtotext(e?.x!!.toInt()-1)+
                                com.example.finallydevice.share.TypeConverter.timetotext(e?.y!!.toLong()),
                            Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onNothingSelected() {
                }
            }
        )
        bar.invalidate()
    }
    fun <T> setdata(list:List<T>,count: Int,totaltime:Long){
        if(!datalist.isEmpty())
            datalist.clear()
        if (count==7)
        {
            var list=list.toMutableList() as MutableList<Appusingdata_day_database>
            list.add(Appusingdata_day_database(0,"","",totaltime, LocalDate.now()))
            datalistadd(7)
            list.forEach {
                datalist.set(it.date.dayOfWeek.value-1,BarEntry(it.date.dayOfWeek.value.toFloat(),it.totaltime.toFloat()))
            }
            datelist_day=list
        }
        if(count==24)
        {
            var list=list.toMutableList() as MutableList<Appusingdata_hour_database>
            val appuse= AppUsingTime(context)
            var list_now= emptyList<Appusingdata_hour_database>()
            datalistadd(24)
                list_now=appuse.getappusingtime<Appusingdata_hour_database>(LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)), LocalDateTime.now(),LocalTime.now().hour).filter {
                    it.packagename == appinfo.packagename
               }
                Log.d(".,",list_now.toString())
            if(!list_now.isEmpty())
                list.add(list_now[0])
                if(!list.isEmpty())
                {
                    Log.d("popopo",list.toString())
                    datalist.set(list[0].starttime,
                        BarEntry(list[0].starttime+1f,list[0].totaltime.toFloat())
                    )
                    for(i in 1..list.size-1)
                        datalist.set(list[i].starttime, BarEntry(list[i].starttime+1f,(list[i].totaltime-list[i-1].totaltime).toFloat()))
                    Log.d("popopo",datalist.toString())
                }
            datelist_hour=list
        }
        when(count)
        {

            7->{
                while (datalist.size<7)
                    datalist.add(BarEntry(datalist.size+1f,0f))
            }
            24->{
                while (datalist.size<24)
                    datalist.add(BarEntry(datalist.size+1f,0f))
                Log.d("...",datalist.toString())
                }
        }

    }
    private fun datalistadd(n:Int){
        for(i in 1..n)
            datalist.add(BarEntry(i.toFloat(), 0f))
    }
}