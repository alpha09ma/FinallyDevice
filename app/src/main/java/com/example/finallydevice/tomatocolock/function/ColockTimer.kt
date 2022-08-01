package com.example.finallydevice.tomatocolock.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.properties.Delegates

class ColockTimer {
    private lateinit var timer: Timer
    private var hours:Int = 0
    private var minutes:Int = 0
    private var seconds:Int = 0
    private lateinit var result:String
    private lateinit var listener:(String)->Unit
    fun starttime(timesum: String,listener:(String)->Unit) {
        hours = timesum.substring(0, 2).toInt()
        minutes = timesum.substring(3, 5).toInt()
        seconds = timesum.substring(6).toInt()
        this.listener=listener
        begin()
    }
    fun begin(){
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                if (seconds < 0) {
                    seconds = 59
                    minutes--;
                }
                if (minutes < 0) {
                    minutes = 59
                    hours--;
                }
                result = String.format("%02d", hours) + ":" + String.format(
                    "%02d",
                    minutes
                ) + ":" + String.format("%02d", seconds)
                listener(result)
                seconds--;
            }
        }
        timer.schedule(task, 0, 1000)
    }
    fun cancel(){
        timer.cancel()
    }
}