package com.example.finallydevice.share.Service_function


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.*
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.finallydevice.R
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_day_database
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.example.finallydevice.MainActivity
import com.example.finallydevice.ScreenStatics.home.ScreenUsingTimer
import com.example.finallydevice.ScreenStatics.home.dataclass.Screenusingdata
import com.example.finallydevice.share.Service_function.InnerService.Companion.NOTIFICATION_ID
import com.example.finallydevice.share.Service_function.InnerService.Companion.channelid
import com.example.finallydevice.repositry.AppusingRepository
import com.example.finallydevice.repositry.ScreenusingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class ScreenUsingTime : Service(){
    companion object{
        val CLASSTYPE_L:String="Long"
    }
    private lateinit var sp:SharedPreferences
    private var firststart: Boolean =true
    private val receiver=Receiver()
    private lateinit var timer: ScreenUsingTimer
    private lateinit var screpository:ScreenusingRepository
    private lateinit var aprepository:AppusingRepository
    private lateinit var usingtime: AppUsingTime
    inner class Receiver: BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action ==Intent.ACTION_SCREEN_ON)
            {
                GlobalScope.launch(Dispatchers.IO) {
                    screpository.save_extra("screenon",System.currentTimeMillis())
                }
                var count = screpository.get_extra<Long>("count", CLASSTYPE_L)
                GlobalScope.launch (Dispatchers.IO){
                    if(count==0L)
                        count+=1
                    screpository.save_extra("count",count+1)
                }
                GlobalScope.launch(Dispatchers.IO) {
                    val m1=screpository.get_extra<Long>("offtime","Long")
                    var m2=0L
                    if(screpository.get_extra<Long>("screenoff","Long")!=0L)
                       m2=System.currentTimeMillis()-screpository.get_extra<Long>("screenoff","Long")
                    if(m1<m2)
                        screpository.save_extra("offtime",m2)
                }
                timer.starttime()
                Log.d("receiver?","screenon")
            }
            else
                if (intent?.action==Intent.ACTION_SCREEN_OFF)
                {
                GlobalScope.launch(Dispatchers.IO) {
                    screpository.save_extra("screenoff",System.currentTimeMillis())
                }
                save_time()
                GlobalScope.launch(Dispatchers.IO) {
                    val scon=screpository.get_extra<Long>("screenon", CLASSTYPE_L)
                    val m1=screpository.get_extra<Long>("max","Long")
                    val m2=System.currentTimeMillis()-scon
                    if(m1<m2)
                        screpository.save_extra("max",m2)
                    screpository.save_extra("screen",0)
                }
                timer.timecancel()
                }
            if(intent?.action==Intent.ACTION_TIME_TICK)
            {
                val hour_now=LocalTime.now().hour
                val minute_now=LocalTime.now().minute
                GlobalScope.launch(Dispatchers.IO) {
                    val _sum=screpository.get_extra<Long>("screen", CLASSTYPE_L)
                    val sum=screpository.get_extra<Long>("sum", CLASSTYPE_L)
                    if(_sum==0L)
                       screpository.save_extra("screen",sum+60L*1000L)
                    else
                        screpository.save_extra("screen",_sum+60L*1000L)
                    if(_sum>screpository.get_extra<Long>("max", CLASSTYPE_L))
                        screpository.save_extra("screen",_sum)
                }
                if(minute_now==59)
                {
                    val time1= LocalDateTime.of(
                        LocalDate.now(), LocalTime.of(0,0,0))
                    val time2= LocalDateTime.now()
                    val datalist=usingtime.getappusingtime<Appusingdata_hour_database>(time1,time2,time2.hour)
                    val datalist2 =  aprepository.getinputtimehour(time2.hour)
                        if(datalist2.value.isNullOrEmpty())
                            datalist.forEach {
                                GlobalScope.launch (Dispatchers.IO){
                                    aprepository.addtimehour(it)
                                }
                            }
                        Log.d("whathappend","addsucess"+datalist.size)
                }
                if(hour_now==0&&minute_now==0)
                {
                    timer.timecancel()
                    GlobalScope.launch (Dispatchers.IO){
                        val time_now=LocalDate.now().plusDays(-1)
                        var sum=0L
                        if(screpository.get_extra<Long>("screenon", CLASSTYPE_L)> screpository.get_extra<Long>("screenoff", CLASSTYPE_L))
                          sum=screpository.get_extra<Long>("sum", CLASSTYPE_L) +System.currentTimeMillis()-screpository.get_extra<Long>("screenon",
                            CLASSTYPE_L
                          )
                        else
                            sum=screpository.get_extra<Long>("sum", CLASSTYPE_L)
                        val count=screpository.get_extra<Long>("count", CLASSTYPE_L)
                        val offtime=screpository.get_extra<Long>("offtime", CLASSTYPE_L)
                        val usingmax=screpository.get_extra<Long>("max", CLASSTYPE_L)
                        val usingavg=sum/count
                        val datalist2=screpository.getinputtimedata(time_now)
                        if(datalist2.value.isNullOrEmpty())
                            screpository.addtimedata(Screenusingdata(0,time_now,sum, count,usingmax,usingavg,offtime))
                    }
                    GlobalScope.launch(Dispatchers.IO) {
                        screpository.save_extra("sum",0L)
                        timer.starttime()
                    }
                    GlobalScope.launch(Dispatchers.IO) {
                        screpository.save_extra("count",1L)
                    }
                    GlobalScope.launch (Dispatchers.IO){
                        screpository.save_extra("screenon",System.currentTimeMillis())
                    }
                    GlobalScope.launch (Dispatchers.IO){
                        screpository.save_extra("offtime",0)
                    }
                    GlobalScope.launch (Dispatchers.IO){
                        screpository.save_extra("max",0L)
                    }
                    GlobalScope.launch (Dispatchers.IO){
                        screpository.save_extra("screen",0L)
                    }
                    val datalist=usingtime.getappusingtime<Appusingdata_day_database>(LocalDateTime.of(LocalDate.now().plusDays(-1),
                        LocalTime.of(0,0,0)),LocalDateTime.now(),25)
                    val datalist2=screpository.getinputtimedata(LocalDate.now().plusDays(-1))
                    if(datalist2.value.isNullOrEmpty())
                        datalist.forEach {
                            GlobalScope.launch {
                            aprepository.addtimeday(it)
                            }
                        }
                    GlobalScope.launch (Dispatchers.IO){
                        aprepository.deletealltimetoday()
                    }
                    timer.starttime()
                }
            }
        }
    }
    fun save_time()
    {
        val lasttime=get_extra("screenon")
        var sum=get_extra("sum")
        sum+=System.currentTimeMillis()-lasttime
        save_extra("sum",sum)
        Log.d("receiver?","screenoff")
    }
    fun save_extra(type:String,num:Long)
    {
        val editor=sp.edit()
        editor.putLong(type,num)
        editor.commit()
    }
    fun get_extra(type: String):Long
    {
        return sp.getLong(type,0L)
    }
    inner class Bind:Binder(){
        fun getService(): ScreenUsingTime? {
            return this@ScreenUsingTime
        }
    }
    override fun onCreate() {
        val filter: IntentFilter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_TIME_TICK)
        registerReceiver(receiver, filter)
        super.onCreate()
        Log.d(".....","startsuccessfully")
        screpository= ScreenusingRepository(this.applicationContext)
        usingtime= AppUsingTime(applicationContext)
        aprepository= AppusingRepository(applicationContext)
        val manager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(NotificationChannel(channelid,"test",NotificationManager.IMPORTANCE_LOW))
        val intent=Intent(applicationContext,MainActivity::class.java)
        intent.setAction(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        val pendingintent=PendingIntent.getActivity(applicationContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val builder =  NotificationCompat.Builder(this, channelid)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setContentTitle("不存在的应用")
        builder.setContentText("正在运行中")
        builder.setContentIntent(pendingintent)
        startForeground(NOTIFICATION_ID, builder.build())
        startService(Intent(this, InnerService::class.java))
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("receiver?","已启动")
        if(firststart)
        {
            Log.d("receiver?",firststart.toString())
            firststart=false
            sp=getSharedPreferences("screentime",Context.MODE_PRIVATE)
            save_extra("screenon",System.currentTimeMillis())
            timer= ScreenUsingTimer(screpository)
            timer.starttime()
            Log.d("???",sp.getLong("sum",0L).toString())
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        timer.timecancel()
        Log.d("destroy",LocalTime.now().toString())
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}