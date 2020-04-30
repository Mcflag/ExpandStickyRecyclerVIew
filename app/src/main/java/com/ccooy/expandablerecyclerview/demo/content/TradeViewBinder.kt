package com.ccooy.expandablerecyclerview.demo.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ccooy.expandablerecyclerview.R
import com.ccooy.expandablerecyclerview.demo.ContentHolder
import com.ccooy.expandablerecyclerview.demo.FrameBinder

class TradeViewBinder : FrameBinder<TradeView, TradeViewBinder.ViewHolder>() {

  override fun onCreateContentViewHolder(inflater: LayoutInflater, parent: ViewGroup): ContentHolder {
    return ViewHolder(inflater.inflate(R.layout.demo_trade, parent, false))
  }

  override fun onBindContentViewHolder(holder: ViewHolder, content: TradeView, position: Int) {
    holder.typeText.setText(content.tradeItem.type)
    holder.totalText.setText(content.tradeItem.total)
  }

  class ViewHolder(itemView: View) : ContentHolder(itemView) {
    val typeText: TextView = itemView.findViewById(R.id.type)
    val totalText: TextView = itemView.findViewById(R.id.total)
  }
}
