package com.ccooy.expandablerecyclerview.demo.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ccooy.expandablerecyclerview.R
import com.ccooy.expandablerecyclerview.demo.ContentHolder
import com.ccooy.expandablerecyclerview.demo.FrameBinder

class DiscountViewBinder : FrameBinder<DiscountView, DiscountViewBinder.ViewHolder>() {

  override fun onCreateContentViewHolder(inflater: LayoutInflater, parent: ViewGroup): ContentHolder {
    return ViewHolder(inflater.inflate(R.layout.demo_discount, parent, false))
  }

  override fun onBindContentViewHolder(holder: ViewHolder, content: DiscountView, position: Int) {
    holder.discountText.text = content.discountItem.discount
  }

  class ViewHolder(itemView: View) : ContentHolder(itemView) {
    val discountText: TextView = itemView.findViewById(R.id.discount)
  }
}
