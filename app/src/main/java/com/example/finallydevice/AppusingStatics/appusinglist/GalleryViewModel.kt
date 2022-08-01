package com.example.finallydevice.AppusingStatics.appusinglist

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_day_database
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.example.finallydevice.repositry.AppusingRepository
import com.example.finallydevice.share.Service_function.AppUsingTime
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class GalleryViewModel(context: Context) : ViewModel() {
    val appuse= AppUsingTime(context)
    val appusinghourdatabase:LiveData<List<Appusingdata_hour_database>>
    val appusingdaydatabase:LiveData<List<Appusingdata_day_database>>
    private val repository: AppusingRepository
    private lateinit var list0:MutableList<Appusingdata_hour_database>
    val list_start get() = list0
    init {
        repository=AppusingRepository(context)
        appusinghourdatabase=repository.appusinghourdatabase
        appusingdaydatabase=repository.getallappusingday()

    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private val _list= MutableLiveData<List<Appusingdata_hour_database>>().apply {
            value = appuse.getinstallapp()
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    val list: LiveData<List<Appusingdata_hour_database>> = _list
    @RequiresApi(Build.VERSION_CODES.Q)
    fun setdata(list: List<Appusingdata_hour_database>)
    {
        _list.postValue(list)
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun recover(){
        _list.postValue(list0)
    }
    fun addappusinghour(begintime:LocalDateTime,endtime:LocalDateTime,starttime:Int){
        viewModelScope.launch {
            val list=repository.getusingtime(begintime,endtime,starttime)
            list.forEach {
                repository.addtimehour(it)
            }
        }
    }
    fun addappusingday(begintime:LocalDateTime,endtime:LocalDateTime){
        viewModelScope.launch {
            val list=repository.getusingtime(begintime,endtime)
            list.forEach {
                repository.addtimeday(it)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun getappusinglisttoday(){
        viewModelScope.launch {
            list0 = repository.getappusinglisttoday()
            list0.sortByDescending {
                it.totaltime
            }
            _list.postValue(list0)
        }
    }
}