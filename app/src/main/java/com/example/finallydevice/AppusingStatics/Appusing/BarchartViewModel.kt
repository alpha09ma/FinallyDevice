package com.example.finallydevice.AppusingStatics.Appusing

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_day_database
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.example.finallydevice.repositry.AppusingRepository

class BarchartViewModel(context: Context,appinfo:Appusingdata_hour_database) : ViewModel() {
    private val repository:AppusingRepository
    val appusinghour:LiveData<List<Appusingdata_hour_database>>
    val appusingday:LiveData<List<Appusingdata_day_database>>

    init {
        repository=AppusingRepository(context)
        appusingday=repository.getinputtimeweek(appinfo.packagename)
        appusinghour=repository.getinputtimehour(appinfo.packagename)

    }


}