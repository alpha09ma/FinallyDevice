package com.example.finallydevice.ScreenStatics.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.finallydevice.ScreenStatics.home.dataclass.Statistics_usingtime
import com.example.finallydevice.repositry.ScreenusingRepository
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(context: Context) : ViewModel(){
    private var sum:Int=0
    private var timer: Timer?=null
    private val repositry:ScreenusingRepository
    init {
        repositry= ScreenusingRepository(context)
    }
    val time:MutableLiveData<String> = MutableLiveData<String>().apply {
        value="00:00:00"
    }
    val time_statistics:MutableLiveData<List<Statistics_usingtime>> = MutableLiveData<List<Statistics_usingtime>>().apply {
        value= emptyList()
    }
    private val sp=context.getSharedPreferences("screentime",Context.MODE_PRIVATE)
    fun starttime(){
        timer=Timer()
        val task = object : TimerTask() {
            override fun run() {
                viewModelScope.launch {
                    time.postValue(repositry.get_extra("intime","String"))
                }
            }
        }
        time_statistics.postValue(repositry.getllist())
        timer?.schedule(task,0,1000)
    }
    fun endtime(){
        timer?.cancel()
        timer=null
    }

}