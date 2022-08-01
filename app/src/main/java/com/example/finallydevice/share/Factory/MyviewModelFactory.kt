package com.example.finallydevice.share.Factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finallydevice.AppusingStatics.AppusingPieChart.AppusingStaticsViewModel
import com.example.finallydevice.AppusingStatics.appusinglist.GalleryViewModel
import com.example.finallydevice.ScreenStatics.home.HomeViewModel
import com.example.finallydevice.share.ShareViewModel
import com.example.finallydevice.AppusingStatics.Appusing.BarchartViewModel
import com.example.finallydevice.ScreenStatics.screenbar.ScreenstaticsViewModel
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database

class MyviewModelFactory(val app:Application):ViewModelProvider.AndroidViewModelFactory(app) {
    private lateinit var appinfo: Appusingdata_hour_database
    private lateinit var type:String
    constructor(app1: Application,appinfo:Appusingdata_hour_database) : this(app1)
    {
        this.appinfo=appinfo
    }
    constructor(app2:Application,type:String):this(app2)
    {
        this.type=type
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShareViewModel::class.java))
            return ShareViewModel(app) as T
        if(modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(app) as T
        if(modelClass.isAssignableFrom(GalleryViewModel::class.java))
            return GalleryViewModel(app.applicationContext) as T
        if(modelClass.isAssignableFrom(AppusingStaticsViewModel::class.java))
            return AppusingStaticsViewModel(app.applicationContext) as T
        if(modelClass.isAssignableFrom(BarchartViewModel::class.java))
            return BarchartViewModel(app.applicationContext,appinfo) as T
        if(modelClass.isAssignableFrom(ScreenstaticsViewModel::class.java))
            return ScreenstaticsViewModel(app.applicationContext,type) as T
        return super.create(modelClass)
    }
}