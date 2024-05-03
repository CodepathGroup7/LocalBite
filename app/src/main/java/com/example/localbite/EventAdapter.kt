package com.example.localbite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.data.model.Event
import com.google.gson.Gson
import java.io.Serializable

class EventAdapter(eventModelList: List<Event>):
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private val eventModelList: List<Event>
    val gson = Gson()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val eventName: TextView
        val eventDesc: TextView
        val eventDate: TextView

        init {
            eventName = itemView.findViewById(R.id.eventName)
            eventDesc = itemView.findViewById(R.id.eventDesc)
            eventDate = itemView.findViewById(R.id.eventDate)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            val intent = Intent(context, EventDetails::class.java)

            val event = eventModelList[adapterPosition]
            intent.putExtra("id", event.id)

            context.startActivity(intent)
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