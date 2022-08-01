package com.example.finallydevice.share.Service_function
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_day_database
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import java.time.*
import java.util.*

class AppUsingTime(val context: Context) {
    private lateinit var timer: Timer
    fun <T>getappusingtime(begintime:LocalDateTime,endtime:LocalDateTime,starttime:Int):List<T>{
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        //val date_1=LocalDateTime.of(
            //LocalDate.now(), LocalTime.of(0,0,0)).atZone(ZoneId.of(TimeZone.getDefault().id)).toEpochSecond()*1000L
        //val date_2=LocalDateTime.now().atZone(ZoneId.of(TimeZone.getDefault().id)).toEpochSecond()*1000L
        /*val list=  usageStatsManager.queryUsageStats(
            System.currentTimeMillis()-1000*60*60*23,
            System.currentTimeMillis()
        ).filter {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                it.value.totalTimeVisible>=0L
            } else {
                TODO("VERSION.SDK_INT < Q")
            }
        }*/
        val time1=begintime.atZone(ZoneId.of(TimeZone.getDefault().id)).toEpochSecond()*1000L
        val time2=endtime.atZone(ZoneId.of(TimeZone.getDefault().id)).toEpochSecond()*1000L
        val list1=usageStatsManager.queryEvents(time1,time2)
        val list2:UsageEvents.Event=UsageEvents.Event()
        val map1:MutableMap<String,Long> = mutableMapOf()
        val map2:MutableMap<String,Long> = mutableMapOf()
        while (list1.getNextEvent(list2))
        {
            //Log.d("ppp",list2.packageName+" "+list2.eventType+" "+list2.timeStamp)
            if(list2.eventType==UsageEvents.Event.ACTIVITY_RESUMED)
            {
                Log.d("whathappen",list2.packageName+list2.timeStamp)
                map1.put(list2.packageName,list2.timeStamp)
            }
            if(list2.eventType==UsageEvents.Event.ACTIVITY_PAUSED)
            {
                Log.d("whathappen",list2.packageName+list2.timeStamp)
                if(map1.contains(list2.packageName))
                {
                    if(map2.contains(list2.packageName))
                        map2.replace(list2.packageName,map2.getValue(list2.packageName)+list2.timeStamp-map1.getValue(list2.packageName))
                    else
                        map2.put(list2.packageName,list2.timeStamp-map1.getValue(list2.packageName))
                }
                else
                {
                    map2.put(list2.packageName,list2.timeStamp-time1)
                }
            }
        }
        map1.forEach { key, value ->
            if(!map2.containsKey(key))
                map2.put(key,time2-value)
        }
        val usingtimelist= emptyList<Appusingdata_hour_database>().toMutableList()
        val usingtimelist_day = emptyList<Appusingdata_day_database>().toMutableList()
        val packageManager:PackageManager=context.packageManager
        //val intent = Intent();
        //intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //intent.setAction(Intent.ACTION_MAIN);

        //val visiblelist=packageManager.getInstalledPackages(0)
        /*for(i in list)
        {
            val applicationInfo=packageManager.getApplicationInfo(i.value.packageName,PackageManager.MATCH_DISABLED_COMPONENTS)
            val name=applicationInfo.loadLabel(packageManager).toString()
            val icon=packageManager.getApplicationIcon(applicationInfo)
            Log.d("iiiii",""+i.value.packageName+i.value.totalTimeVisible+"  "+LocalDateTime.ofEpochSecond(i.value.firstTimeStamp/1000,0, ZoneOffset.of("Z"))+""+LocalDateTime.ofEpochSecond(i.value.lastTimeStamp/1000,0, ZoneOffset.of("+0")))
                //for(j in visiblelist)
                //{
                    //val applicationInfo1=packageManager.getPackageInfo(j.packageName,PackageManager.GET_META_DATA).applicationInfo
                    //if(applicationInfo1.packageName==i.packageName)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    usingtimelist.add(Appusingtimedata(icon,i.value.packageName,name,i.value.totalTimeVisible))
                }
        }*/
        if(starttime!=25)
        {
            for(i in map2)
            {
                val applicationInfo=packageManager.getPackageInfo(i.key,PackageManager.GET_META_DATA).applicationInfo
                val name=applicationInfo.loadLabel(packageManager).toString()
                val icon=packageManager.getApplicationIcon(applicationInfo)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    usingtimelist.add(Appusingdata_hour_database(0,icon,i.key,name,i.value,starttime))
                }
            }
            usingtimelist.sortByDescending {
                it.totaltime
            }
            return usingtimelist.toList() as List<T>
        }
        else
        {
            for(i in map2)
            {
                val applicationInfo=packageManager.getPackageInfo(i.key,PackageManager.GET_META_DATA).applicationInfo
                val name=applicationInfo.loadLabel(packageManager).toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    usingtimelist_day.add(Appusingdata_day_database(0,i.key,name,i.value,begintime.toLocalDate()))
                }
            }
            usingtimelist.sortByDescending {
                it.totaltime
            }
            return usingtimelist_day.toList() as List<T>
        }
    }
    fun getinstallapp():List<Appusingdata_hour_database>{
        val packageManager:PackageManager=context.packageManager
        var installedlist=packageManager.getInstalledPackages(0)
        val returnlist= mutableListOf<Appusingdata_hour_database>()

        installedlist = installedlist.filter {
            it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM==0
        }
        installedlist.forEach {
            val applicationInfo=it.applicationInfo
            val name=applicationInfo.loadLabel(packageManager).toString()
            val icon=packageManager.getApplicationIcon(applicationInfo)
            returnlist.add(Appusingdata_hour_database(0,icon,applicationInfo.packageName,name,0,0))
        }
        val intent = Intent();
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setAction(Intent.ACTION_MAIN)
        val installedlist_system = packageManager.queryIntentActivities(intent,PackageManager.MATCH_SYSTEM_ONLY)
        installedlist_system.forEach {
            val applicationInfo=it.activityInfo.applicationInfo
            val name=applicationInfo.loadLabel(packageManager).toString()
            val icon=packageManager.getApplicationIcon(applicationInfo)
            Log.d("..,",applicationInfo.loadLabel(packageManager).toString())
            returnlist.add(Appusingdata_hour_database(0,icon,applicationInfo.packageName,name,0,0))
        }
        return returnlist
    }
}