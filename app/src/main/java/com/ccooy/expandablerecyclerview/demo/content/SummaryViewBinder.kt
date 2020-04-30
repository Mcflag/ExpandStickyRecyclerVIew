package com.ccooy.expandablerecyclerview.demo.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.ccooy.expandablerecyclerview.R
import com.ccooy.expandablerecyclerview.demo.ContentHolder
import com.ccooy.expandablerecyclerview.demo.FrameBinder

class SummaryViewBinder : FrameBinder<SummaryView, SummaryViewBinder.ViewHolder>() {

    override fun onCreateContentViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ContentHolder {
        return ViewHolder(inflater.inflate(R.layout.demo_summary, parent, false))
    }

    override fun onBindContentViewHolder(holder: ViewHolder, content: SummaryView, position: Int) {
        holder.timeText.setText(content.summaryItem.time)
        holder.countText.setText(content.summaryItem.count)
        holder.countText.setText(content.summaryItem.count)
        content.isOpen = !content.isOpen
        holder.container.setOnClickListener{
            if(content.isOpen){
                content.open(position)
            }else{
                content.close(position, content.contentLength)
            }
        }
    }

    class ViewHolder(itemView: View) : ContentHolder(itemView) {
        val timeText: TextView = itemView.findViewById(R.id.time)
        val countText: TextView = itemView.findViewById(R.id.count)
        val container: View = itemView.findViewById(R.id.whole_container)
//        var position: Int = 0

//        init {
//            position = parent.adapterPosition
//            itemView.setOnClickListener { v ->
//                itemView
//                Toast.makeText(
//                    v.context, "Position: $adapterPosition",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
    }
}
