package com.ccooy.expandablerecyclerview.demo.content

import com.ccooy.expandablerecyclerview.demo.Frame
import com.ccooy.expandablerecyclerview.demo.SummaryItem

class SummaryView(
  var summaryItem: SummaryItem,
  var isOpen: Boolean,
  var contentLength: Int,
  var open: (Int) -> Unit,
  var close: (Int, Int) -> Unit
) : Frame(TYPE) {

  companion object {
    const val TYPE = "summary_item"
  }
}
