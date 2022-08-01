package com.example.finallydevice.share.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_day_database
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.example.finallydevice.ScreenStatics.home.dataclass.Screenusingdata
import com.example.finallydevice.todolist_shortterm.todolist.dataclass.ToDoListData
import java.time.LocalDate

@Dao
interface ToDoListDao {
    //insert
    //tododata
    @Insert
    suspend fun add(data: ToDoListData)
    //screenusing
    @Insert
    suspend fun addscreenusingtime(data: Screenusingdata)
    //appusinghour
    @Insert
    suspend fun addappusinghourtime(data: Appusingdata_hour_database)
    //appusingday
    @Insert
    suspend fun addappusingdaytime(data: Appusingdata_day_database)
    //update
    @Update
    suspend fun update(data: ToDoListData)
    //deleteone
    @Delete
    suspend fun delete(data: ToDoListData)
    //query
    @Query("SELECT * FROM Todolist ORDER BY id ASC")
    fun getAllToDoData():LiveData<List<ToDoListData>>
    @Query("SELECT * FROM Todolist ORDER BY degree ASC")
    fun sortbydegree():LiveData<List<ToDoListData>>
    //screenusingdatainput
    @Query("SELECT * FROM screenusing Where date==:input")
    fun getinputdayscreenusingdata(input:LocalDate):LiveData<List<Screenusingdata>>
    //appusingdataall
    @Query("SELECT * FROM appusing_hour")
    fun getallappusinghour():LiveData<List<Appusingdata_hour_database>>
    @Query("SELECT * FROM appusing_hour Where starttime==:inputtime")
    fun getinputappusinghour(inputtime:Int):LiveData<List<Appusingdata_hour_database>>
    @Query("SELECT * FROM appusing_hour Where packagename==:inputname")
    fun getinputappusinghour(inputname:String):LiveData<List<Appusingdata_hour_database>>
    //appusingdatainput
    @Query("SELECT * FROM appusing_day Where date==:input AND :name==name")
    fun getinputAppusingDay(input: LocalDate,name:String):LiveData<List<Appusingdata_day_database>>
    @Query("SELECT * FROM appusing_day Where date>=:mindate AND date<=:maxdate AND :input==packagename")
    fun getinputAppusingDay(input: String,mindate: LocalDate,maxdate: LocalDate):LiveData<List<Appusingdata_day_database>>
    @Query("SELECT * FROM appusing_day Where date==:input")
    fun getinputAppusingDay(input: LocalDate):LiveData<List<Appusingdata_day_database>>
    @Query("SELECT * FROM appusing_day")
    fun getallAppusingDay():LiveData<List<Appusingdata_day_database>>
    @Query("SELECT * FROM screenusing")
    fun getallScreenusingdata():LiveData<List<Screenusingdata>>
    @Query("SELECT * FROM screenusing Where date>=:mindate AND date<=:maxdate")
    fun getinputScreenusingdata(mindate: LocalDate,maxdate: LocalDate):LiveData<List<Screenusingdata>>
    @Query("SELECT * FROM screenusing Where date==:date")
    fun getinputScreenusingdata(date: LocalDate):LiveData<List<Screenusingdata>>
    //Delete
    @Query("DELETE From Todolist")
    suspend fun deletealltodolist()
    @Query("DELETE From appusing_day")
    suspend fun deleteallappusingdataday()
    @Query("DELETE From appusing_hour")
    suspend fun deleteallappusingdatatoday()
    @Query("DELETE From screenusing")
    suspend fun deleteallusingdata()
}