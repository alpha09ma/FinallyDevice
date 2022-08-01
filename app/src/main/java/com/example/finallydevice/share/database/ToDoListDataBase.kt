package com.example.finallydevice.share.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_day_database
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.example.finallydevice.ScreenStatics.home.dataclass.Screenusingdata
import com.example.finallydevice.todolist_shortterm.todolist.dataclass.ToDoListData

@Database(entities = [ToDoListData::class,Screenusingdata::class,Appusingdata_hour_database::class,Appusingdata_day_database::class], version = 2,exportSchema=false)
@TypeConverters(DateTypeConverter::class)
abstract class ToDoListDataBase:RoomDatabase() {
    abstract fun myDataDao():ToDoListDao
}