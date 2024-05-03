package com.example.localbite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventDetailsAdapter (eventParticipants: List<String>):
RecyclerView.Adapter<EventDetailsAdapter.ViewHolder>() {

    private val eventParticipants: List<String>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memberName: TextView = itemView.findViewById(R.id.member_user_name)
        val image: ImageView = itemView.findViewById(R.id.member_image)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventDetailsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.member_list_item, parent, false)
        return ViewHolder(view)
    }

    init {
        this.eventParticipants = eventParticipants
    }
    override fun onBindViewHolder(holder: EventDetailsAdapter.ViewHolder, position: Int) {
        val member: String = eventParticipants[position]
        holder.memberName.text = member

    }

    override fun getItemCount(): Int {
        return eventParticipants.size
    }
}