package com.example.finallydevice.ScreenStatics.home.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finallydevice.databinding.FragmentHomeItemBinding
import com.example.finallydevice.ScreenStatics.home.dataclass.Statistics_usingtime

class Screenusingadapter(val listener: Skiptofragment): RecyclerView.Adapter<Screenusingadapter.MyViewHolder>() {
    private var datalist= emptyList<Statistics_usingtime>()
    class MyViewHolder private constructor(val view: FragmentHomeItemBinding,val listener: Skiptofragment):
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: Statistics_usingtime){
            view.state.text=item.state
            view.statistics.text=item.statics
            if(listener!=null)
            view.cardview.setOnClickListener {
                var type:String=""
                when(item.state)
                {
                    "平均查看时长"->{type="usingtimeavg"}
                    "最长连续使用"->{type="usingtimemax"}
                    "最长锁屏时长"->{type="offtime"}
                    "今日拿起次数"->{type="count"}
                }
                listener.next(type)
            }
        }
        companion object{
            fun from(parent: ViewGroup,listener: Skiptofragment): MyViewHolder {
                val layoutinflater= LayoutInflater.from(parent.context)
                val root=FragmentHomeItemBinding.inflate(layoutinflater,parent,false)
                return MyViewHolder(root,listener)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemdata =datalist[position]
        holder.bind(itemdata)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
    fun setdata(datalist:List<Statistics_usingtime>){
        this.datalist=datalist
        notifyDataSetChanged()
    }
}
