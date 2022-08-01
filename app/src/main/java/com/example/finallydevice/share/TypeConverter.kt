package com.example.finallydevice.share

import java.time.LocalDate

class TypeConverter {
    companion object{
        fun timetotext(time:Long):String{
            var sum=time
            sum/=1000
            val hours=sum/3600
            val minutes=(sum-hours*3600)/60
            var result:String
            if(hours>0)
                result="${hours}小时${minutes}分钟"
            else
                if(minutes>0)
                    result="${minutes}分钟"
                else
                    if(time==0L)
                        result="未使用"
                    else
                        result="不足1分钟"
            return result
        }
        fun datetotext(date:LocalDate):String
        {
            val array = arrayOf("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月")
            return array[date.month.value-1]+date.dayOfMonth+"日"
        }
        fun hourtotext(starttime:Int):String{
            val array = arrayOf("0:00-1:00","1:00-2:00","2:00-3:00","3:00-4:00","4:00-5:00","5:00-6:00","6:00-7:00","7:00-8:00","8:00-9:00","9:00-10:00","10:00-11:00","11:00-12:00",
            "12:00-13:00","13:00-14:00","14:00-15:00","15:00-16:00","16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00","20:00-21:00","21:00-22:00","22:00-23:00","23:00-0:00")
            return array[starttime]
        }
    }
}