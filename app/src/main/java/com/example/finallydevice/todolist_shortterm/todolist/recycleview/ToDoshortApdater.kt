package com.example.finallydevice.todolist_shortterm

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finallydevice.databinding.TodolistItemBinding
import com.example.finallydevice.todolist_shortterm.todolist.recycleview.SkiptoColock
import com.example.finallydevice.todolist_shortterm.todolist.dataclass.ToDoListData

class MyAdapter(private val listener: SkiptoColock?): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var datalist= emptyList<ToDoListData>()
    class MyViewHolder private constructor(val view:TodolistItemBinding,val listener: SkiptoColock?):RecyclerView.ViewHolder(view.root) {

        fun bind(item: ToDoListData){
            view.todo.text=item.description
            view.type.text=item.type
            view.timecolock.text= item.time
            when(item.degree)
            {
                4->{view.degree.setBackgroundColor(Color.RED)}
                3->{view.degree.setBackgroundColor(Color.YELLOW)}
                2->{view.degree.setBackgroundColor(Color.GREEN)}
            }
            if(listener!=null)
            {
                view.floatingActionButton2.setOnClickListener{
                    listener.onPlayerclicked(view.timecolock.text.toString())
                }
            }
        }
        companion object{
            fun from(parent:ViewGroup,listener: SkiptoColock?): MyViewHolder {
                val layoutinflater=LayoutInflater.from(parent.context)
                val root=TodolistItemBinding.inflate(layoutinflater,parent,false)
                return MyViewHolder(root,listener)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemdata =datalist[position]
        holder.view.listItem.setOnClickListener{
            listener?.showdialog(itemdata,position)
        }
        holder.bind(itemdata)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
    fun setdata(list: List<ToDoListData>){
        this.datalist=list
        notifyDataSetChanged()
    }
}
