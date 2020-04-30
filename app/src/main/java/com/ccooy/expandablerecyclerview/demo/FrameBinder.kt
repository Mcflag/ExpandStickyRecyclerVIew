package com.ccooy.expandablerecyclerview.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.ccooy.expandablerecyclerview.R
import com.drakeet.multitype.ItemViewBinder

abstract class FrameBinder<Content : Frame, SubViewHolder : ContentHolder> : ItemViewBinder<FrameItem, FrameBinder.FrameHolder>() {

  protected abstract fun onCreateContentViewHolder(inflater: LayoutInflater, parent: ViewGroup): ContentHolder

  protected abstract fun onBindContentViewHolder(holder: SubViewHolder, content: Content, position: Int)

  override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): FrameHolder {
    val root = inflater.inflate(R.layout.demo_frame, parent, false)
    val subViewHolder = onCreateContentViewHolder(inflater, parent)
    return FrameHolder(root, subViewHolder, this)
  }

  override fun onBindViewHolder(holder: FrameHolder, item: FrameItem) {
    val weiboContent = item.content
    @Suppress("UNCHECKED_CAST")
    onBindContentViewHolder(holder.subViewHolder as SubViewHolder, weiboContent as Content, getPosition(holder))
  }

  class FrameHolder(itemView: View, val subViewHolder: ContentHolder, binder: FrameBinder<*, *>) : RecyclerView.ViewHolder(itemView) {

    private val container: FrameLayout = itemView.findViewById(R.id.container)

    init {
      container.addView(subViewHolder.itemView)
      this.subViewHolder.parent = this

//      itemView.setOnClickListener { v -> Toast.makeText(v.context, "Position: $adapterPosition", LENGTH_SHORT).show() }
//      close.setOnClickListener {
//        val position = adapterPosition
//        if (position != RecyclerView.NO_POSITION) {
//          binder.adapter.items.toMutableList()
//            .apply {
//              removeAt(position)
//              binder.adapter.items = this
//            }
//          binder.adapter.notifyItemRemoved(position)
//        }
//      }
    }
  }
}
