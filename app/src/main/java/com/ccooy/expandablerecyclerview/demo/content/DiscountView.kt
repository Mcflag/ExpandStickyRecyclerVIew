package com.ccooy.expandablerecyclerview.demo.content

import com.ccooy.expandablerecyclerview.demo.DiscountItem
import com.ccooy.expandablerecyclerview.demo.Frame

class DiscountView(var discountItem: DiscountItem) : Frame(TYPE) {
  companion object {
    const val TYPE = "discount_item"
  }
}
