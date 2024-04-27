package com.example.localbite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localbite.data.model.Offer

class OfferAdapter(private val offerModelList: List<Offer>): RecyclerView.Adapter<OfferAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val offerTitle: TextView = itemView.findViewById(R.id.offerTitle)
        val offerDesc: TextView = itemView.findViewById(R.id.offerDesc)
        val offerStartDate: TextView = itemView.findViewById(R.id.offerStartDate)
        val offerEndDate: TextView = itemView.findViewById(R.id.offerEndDate)
        val offerAmount: TextView = itemView.findViewById(R.id.offerAmount)
        val offerCouponCode: TextView = itemView.findViewById(R.id.offerCouponCode)
        val offerPic: ImageView = itemView.findViewById(R.id.offerPic)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.offer_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferAdapter.ViewHolder, position: Int) {
        val model: Offer = offerModelList[position]
        holder.offerTitle.text = model.title
        holder.offerDesc.text = model.description
        holder.offerStartDate.text = model.startDate
        holder.offerEndDate.text = model.endDate
        holder.offerAmount.text = model.discountAmount + "% off"
        holder.offerCouponCode.text = "Coupon Code: " + model.couponCode

    }

    override fun getItemCount(): Int {
        return offerModelList.size
    }
}
