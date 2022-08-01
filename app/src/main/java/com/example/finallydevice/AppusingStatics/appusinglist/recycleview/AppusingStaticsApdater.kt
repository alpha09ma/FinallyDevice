package com.example.finallydevice.AppusingStatics.appusinglist.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finallydevice.databinding.FragmentAppstaticsItemBinding
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.example.finallydevice.share.TypeConverter

class AppusingStaticsApdater(val listener: toOneAppStatics):RecyclerView.Adapter<AppusingStaticsApdater.MyViewHolder>() {
    private var datalist= emptyList<Appusingdata_hour_database>()
    class MyViewHolder private constructor(val view: FragmentAppstaticsItemBinding,val listener: toOneAppStatics):
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: Appusingdata_hour_database){
            view.appicon.setImageDrawable(item.icon)
            view.appname.text=item.name
            view.appusingtime.text=TypeConverter.timetotext(item.totaltime)
        }
        companion object{
            fun from(parent: ViewGroup,listener: toOneAppStatics): MyViewHolder {
                val layoutinflater= LayoutInflater.from(parent.context)
                val root= FragmentAppstaticsItemBinding.inflate(layoutinflater,parent,false)
                return MyViewHolder(root,listener)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent,listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemdata =datalist[position]
        holder.view.appstaticsitem.setOnClickListener {
            listener.onclicked(itemdata)
        }
        holder.bind(itemdata)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
    fun setdata(datalist:List<Appusingdata_hour_database>){

        this.datalist=datalist
        notifyDataSetChanged()
    }
}