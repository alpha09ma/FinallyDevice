package com.example.finallydevice.share.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun datetoToTimestamp(date:LocalDate):Long{
        return date.toEpochDay()
    }
    @TypeConverter
    fun Timestamptodate(epochday:Long):LocalDate
    {
        return LocalDate.ofEpochDay(epochday)
    }
}