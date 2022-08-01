package com.example.finallydevice.share

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.finallydevice.todolist_shortterm.todolist.dataclass.ToDoListData
import com.example.finallydevice.share.database.ToDoListDataBase
import com.example.finallydevice.repositry.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShareViewModel(context: Context) : ViewModel() {
    private val db by lazy {
        Room.databaseBuilder(context.applicationContext, ToDoListDataBase::class.java,"ToDoList.db")
        .fallbackToDestructiveMigrationOnDowngrade()
        .build()
    }
    private val repository:TodoRepository
    val item: LiveData<List<ToDoListData>>
    init {
        repository=TodoRepository(db)
        item=repository.alldata
    }
    fun add(additem: ToDoListData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(additem)
        }
    }
    fun delete(deleteitem: ToDoListData)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(deleteitem)
        }
    }
    fun update(item: ToDoListData)
    {
        viewModelScope.launch (Dispatchers.IO){
            repository.update(item)
        }
    }
    fun deleteall(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteall()
        }
    }
}