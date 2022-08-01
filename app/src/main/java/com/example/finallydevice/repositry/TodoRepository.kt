package com.example.finallydevice.repositry

import androidx.lifecycle.LiveData
import com.example.finallydevice.ScreenStatics.home.dataclass.Screenusingdata
import com.example.finallydevice.todolist_shortterm.todolist.dataclass.ToDoListData
import com.example.finallydevice.share.database.ToDoListDataBase

class TodoRepository(private val db: ToDoListDataBase){
    private val toDoListDao=db.myDataDao()
    val alldata:LiveData<List<ToDoListData>> = toDoListDao.getAllToDoData()

    val sortbydegree=toDoListDao.sortbydegree()
    suspend fun add(data: ToDoListData)
    {toDoListDao.add(data)}
    suspend fun update(data: ToDoListData)
    {toDoListDao.update(data)}
    suspend fun delete(data: ToDoListData)
    {toDoListDao.delete(data)}
    suspend fun deleteall()
    {toDoListDao.deletealltodolist()}
    fun sortbydegree():LiveData<List<ToDoListData>>
    {
        return toDoListDao.sortbydegree()
    }
    suspend fun addtime(data: Screenusingdata){
        toDoListDao.addscreenusingtime(data)
    }
}