package com.krirll.for_max

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var list : List<Schedule> = listOf()

    fun setList(newList : List<Schedule>) {
        list = newList
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var seriaName : TextView = itemView.findViewById(R.id.seriaName)
        var date : TextView = itemView.findViewById(R.id.date)
        var time : TextView = itemView.findViewById(R.id.time)
        var name : TextView = itemView.findViewById(R.id.showName)
        var tv : TextView = itemView.findViewById(R.id.nameOfTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.seriaName.text = list[position].seriaNumber
        holder.date.text = list[position].date
        holder.time.text = list[position].time
        holder.name.text = list[position].showDescription?.nameOfSerial
        holder.tv.text = list[position].showDescription?.network?.nameOfTV
    }

    override fun getItemCount() = list.size


}