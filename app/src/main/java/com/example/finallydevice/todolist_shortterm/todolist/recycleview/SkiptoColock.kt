package com.example.finallydevice.todolist_shortterm.todolist.recycleview

import com.example.finallydevice.todolist_shortterm.todolist.dataclass.ToDoListData

interface SkiptoColock {
    fun onPlayerclicked(time:String)
    fun showdialog(itemdata: ToDoListData, position: Int)
}