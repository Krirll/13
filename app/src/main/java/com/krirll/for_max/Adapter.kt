package com.krirll.for_max

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var list : List<Holiday> = listOf()

    fun setList(newList : List<Holiday>) {
        list = newList
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var date : TextView = itemView.findViewById(R.id.date)
        var name : TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = list[position].date
        holder.name.text = list[position].name
    }

    override fun getItemCount() = list.size


}