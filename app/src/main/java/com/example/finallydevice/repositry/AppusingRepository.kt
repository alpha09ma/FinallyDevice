package com.example.finallydevice.repositry

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.finallydevice.share.Service_function.AppUsingTime
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_day_database
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.example.finallydevice.share.database.ToDoListDataBase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AppusingRepository(val context: Context) {
    private val db= Room.databaseBuilder(context, ToDoListDataBase::class.java,"ToDoList.db")
        .fallbackToDestructiveMigrationOnDowngrade()
        .build()
    private val _appusinglist:MutableLiveData<List<Appusingdata_hour_database>> = MutableLiveData<List<Appusingdata_hour_database>>()
    val appusinglist:LiveData<List<Appusingdata_hour_database>> = _appusinglist
    val appusinghourdatabase:LiveData<List<Appusingdata_hour_database>> = getallhourtime()
    suspend fun addtimehour(data:Appusingdata_hour_database)
    {
        db.myDataDao().addappusinghourtime(data)
    }
    suspend fun addtimeday(data: Appusingdata_day_database)
    {
        db.myDataDao().addappusingdaytime(data)
    }
    fun getinputtimehour(appname:String): LiveData<List<Appusingdata_hour_database>> {
       return db.myDataDao().getinputappusinghour(appname)
    }
    fun getinputtimehour(starttime: Int): LiveData<List<Appusingdata_hour_database>> {
        return db.myDataDao().getinputappusinghour(starttime)
    }
    fun getinputtimedate(date: LocalDate): LiveData<List<Appusingdata_day_database>> {
        return db.myDataDao().getinputAppusingDay(date)
    }
    fun getallappusingday():LiveData<List<Appusingdata_day_database>>{
        return db.myDataDao().getallAppusingDay()
    }

    fun getinputtimeweek(apppackagename:String):LiveData<List<Appusingdata_day_database>>{
        when(LocalDate.now().dayOfWeek.name)
        {
            "MONDAY"->{return db.myDataDao().getinputAppusingDay(LocalDate.now(),apppackagename)}
            "TUESDAY"->{return db.myDataDao().getinputAppusingDay(apppackagename,LocalDate.now().plusDays(-1),
                LocalDate.now())}
            "WEDNESDAY"->{return db.myDataDao().getinputAppusingDay(apppackagename,LocalDate.now().plusDays(-2),
                LocalDate.now())}
            "THURSDAY"->{return db.myDataDao().getinputAppusingDay(apppackagename,LocalDate.now().plusDays(-3),
                LocalDate.now())}
            "FRIDAY"->{Log.d("testweek","...")
                return db.myDataDao().getinputAppusingDay(apppackagename,LocalDate.now().plusDays(-4),
                    LocalDate.now())}
            "SATURDAY"->{return db.myDataDao().getinputAppusingDay(apppackagename,LocalDate.now().plusDays(-5),
                LocalDate.now())}
        }
        return db.myDataDao().getinputAppusingDay(apppackagename,LocalDate.now().plusDays(-6),
            LocalDate.now())
    }
    fun getallhourtime():LiveData<List<Appusingdata_hour_database>>{
        return db.myDataDao().getallappusinghour()
    }
    suspend fun deletealldaytime(){
        db.myDataDao().deleteallappusingdataday()
    }
    suspend fun deletealltimetoday(){
        db.myDataDao().deleteallappusingdatatoday()
    }
    suspend fun setlist()
    {
        val appusing= AppUsingTime(context)
        var list=getappusinglisttoday()
        list.sortByDescending {
            it.totaltime
        }
        _appusinglist.postValue(list)
    }
    suspend fun getusingtime(begintime:LocalDateTime,endtime:LocalDateTime):List<Appusingdata_day_database>
    {
        val appusing= AppUsingTime(context)
        return appusing.getappusingtime(begintime, endtime,25)
    }
    suspend fun getusingtime(begintime:LocalDateTime,endtime:LocalDateTime,starttime:Int):List<Appusingdata_hour_database>
    {
        val appusing= AppUsingTime(context)
        return appusing.getappusingtime(begintime, endtime,starttime)
    }
    suspend fun getappusinglisttoday():MutableList<Appusingdata_hour_database>
    {
        val appusing= AppUsingTime(context)
        val installedlist:MutableList<Appusingdata_hour_database> = appusing.getinstallapp().toMutableList()
        val usinglist:List<Appusingdata_hour_database> = appusing.getappusingtime(
        LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)),
        LocalDateTime.now(),0)
        val map= mutableMapOf<String,Long>()
        usinglist.forEach {
           map.put(it.packagename,it.totaltime)
        }
        installedlist.forEach {
            if(map.contains(it.packagename))
                it.totaltime = map[it.packagename]!!
        }
        return installedlist
    }
}