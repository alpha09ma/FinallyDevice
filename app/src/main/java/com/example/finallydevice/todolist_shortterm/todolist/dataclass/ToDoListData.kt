package com.example.finallydevice.todolist_shortterm.todolist.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Todolist")
data class ToDoListData(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val type:String,
    val description:String,
    val time:String,
    val degree:Int) : Parcelable
