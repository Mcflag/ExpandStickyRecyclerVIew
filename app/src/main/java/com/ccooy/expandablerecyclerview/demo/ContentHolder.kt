package com.ccooy.expandablerecyclerview.demo

import android.view.View

open class ContentHolder(val itemView: View) {

  lateinit var parent: FrameBinder.FrameHolder

  val adapterPosition: Int
    get() = parent.adapterPosition

  val layoutPosition: Int
    get() = parent.layoutPosition

  val oldPosition: Int
    get() = parent.oldPosition

  var isRecyclable: Boolean
    get() = parent.isRecyclable
    set(recyclable) = parent.setIsRecyclable(recyclable)
}
