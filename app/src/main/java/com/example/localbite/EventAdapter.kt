package com.example.localbite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.data.model.Event

class EventAdapter(eventModelList: List<Event>):
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private val eventModelList: List<Event>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventName: TextView
        val eventDesc: TextView
        val eventDate: TextView
        val eventPic: ImageView

        init {
            eventName = itemView.findViewById(R.id.eventName)
            eventDesc = itemView.findViewById(R.id.eventDesc)
            eventDate = itemView.findViewById(R.id.eventDate)
            eventPic = itemView.findViewById(R.id.eventPic)
        }
    }

    init {
        this.eventModelList = eventModelList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // CHANGE FOR DATABASE
        val model: Event = eventModelList[position]
        holder.eventName.text = model.eventName
        holder.eventDesc.text = model.eventSummary
        holder.eventDate.text = model.eventDate

    }

    override fun getItemCount(): Int {
        return eventModelList.size
    }


}