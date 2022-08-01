package com.example.finallydevice.ScreenStatics.home

import android.util.Log
import com.example.finallydevice.repositry.ScreenusingRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ScreenUsingTimer(val repository: ScreenusingRepository) {
    private var timer: Timer?=null
    private lateinit var result:String
    //private val editor=sp.edit()
    var hours:Int=0
    var minutes:Int=0
    var seconds:Int=0
    var sum=0
fun starttime() {
            timer=Timer()
            sum = (repository.get_extra<Long>("sum","Long")/1000).toInt()
            hours=sum/3600
            minutes=(sum-hours*3600)/60
            seconds=sum%60
            val task = object : TimerTask() {
                override fun run() {
                    if(seconds==60)
                    {
                        seconds=0
                        minutes++

                    }
                    if(minutes==60){
                        minutes=0
                        hours++
                    }
                    result=String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds)
                    GlobalScope.launch {
                        repository.save_extra("intime",result)
                    }
                    Log.d("screentime",result)
                    seconds++
                }
            }
            timer!!.schedule(task,0,1000)
}
    fun timecancel()
    {
        if(timer!=null)
            timer!!.cancel()
    }
}