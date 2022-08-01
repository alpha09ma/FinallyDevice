package com.example.finallydevice.repositry

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.finallydevice.ScreenStatics.home.dataclass.Screenusingdata
import com.example.finallydevice.ScreenStatics.home.dataclass.Statistics_usingtime
import com.example.finallydevice.share.database.ToDoListDao
import com.example.finallydevice.share.database.ToDoListDataBase
import java.time.LocalDate

class ScreenusingRepository(private val context: Context) {
    private var db:ToDoListDataBase=
        Room.databaseBuilder(context.applicationContext, ToDoListDataBase::class.java,"ToDoList.db")
        .fallbackToDestructiveMigrationOnDowngrade()
        .build()
    private var toDoListDao:ToDoListDao=db.myDataDao()
    private val sp=context.getSharedPreferences("screentime",Context.MODE_PRIVATE)
    suspend fun addtimedata(data: Screenusingdata){
        toDoListDao.addscreenusingtime(data)
    }
    fun getinputtimedata(date: LocalDate):LiveData<List<Screenusingdata>>
    {
        return toDoListDao.getinputScreenusingdata(date)
    }
    fun getinputtimedata():LiveData<List<Screenusingdata>>
    {
        when(LocalDate.now().dayOfWeek.name)
        {
            "MONDAY"->{return toDoListDao.getinputScreenusingdata(LocalDate.now(), LocalDate.now())}
            "TUESDAY"->{return toDoListDao.getinputScreenusingdata(LocalDate.now().plusDays(-1),
                LocalDate.now())}
            "WEDNESDAY"->{return toDoListDao.getinputScreenusingdata(LocalDate.now().plusDays(-2),
                LocalDate.now())}
            "THURSDAY"->{return toDoListDao.getinputScreenusingdata(LocalDate.now().plusDays(-3),
                LocalDate.now())}
            "FRIDAY"->{
                return toDoListDao.getinputScreenusingdata(LocalDate.now().plusDays(-4),
                LocalDate.now())}
            "SATURDAY"->{
                return toDoListDao.getinputScreenusingdata(LocalDate.now().plusDays(-5),
                LocalDate.now())}
        }
        return toDoListDao!!.getinputScreenusingdata(LocalDate.now().plusDays(-6),LocalDate.now())
    }
    suspend fun save_extra(type:String,num:Long) {
            val editor=sp.edit()
            editor.putLong(type,num)
            editor.commit()
    }
    suspend fun save_extra(type:String,item:String) {
        val editor=sp.edit()
        editor.putString(type,item)
        editor.commit()
    }
    fun <T> get_extra(type: String,classtype:String):T
    {
        when(classtype)
        {
            "String"->return sp.getString(type,"未得到消息") as T
            "Long"-> return sp.getLong(type,0L) as T
            "Int"-> return sp.getInt(type,0) as T
            "Bool"->return sp.getBoolean(type,false) as T
            "Float"->return sp.getFloat(type,0f) as T
        }
        return null as T
    }
    private fun setlist():List<Statistics_usingtime>{
            var sum_avg=(sp.getLong("screen",0L)+sp.getLong("sum",0L))/sp.getLong("count",1L)/1000
            var sum_max=sp.getLong("max",0L)/1000
            var count=sp.getLong("count",1L)
            var sum_off=sp.getLong("offtime",0L)/1000
            var avgstatisics:String="?"
            var maxstatisics:String="?"
            var offstatisics:String="?"
            avgstatisics=transformtype(sum_avg.toInt())
            maxstatisics=transformtype(sum_max.toInt())
            offstatisics=transformtype(sum_off.toInt())
            return listOf(
                Statistics_usingtime("平均查看时长",avgstatisics),
                Statistics_usingtime("最长连续使用",maxstatisics),
                Statistics_usingtime("最长锁屏时长",offstatisics),
                Statistics_usingtime("今日拿起次数",count.toString()+"次")
            )
        }
    private fun transformtype(sum:Int):String
    {
        var hours:Int=sum/3600
        var minutes:Int=(sum-hours*3600)/60
        val result:String
        if(hours==0)
            result=minutes.toString()+"分钟"
        else
            result=hours.toString()+"小时"+minutes+"分钟"
        return result
    }
    fun getllist():List<Statistics_usingtime>{
        return setlist()
    }
    fun getstatics(_type: String):Long{
        var type:String=_type
        when(_type)
        {
            "usingtime"->type="screen"
            "usingtimemax"-> type="max"
            "usingtimeavg"->
                return (sp.getLong("screen",0L)+sp.getLong("sum",0L))/sp.getLong("count",1L)
        }
        return sp.getLong(type,0L)
    }
}
