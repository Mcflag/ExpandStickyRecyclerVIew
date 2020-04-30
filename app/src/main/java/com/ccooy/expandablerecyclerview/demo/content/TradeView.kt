package com.ccooy.expandablerecyclerview.demo.content

import com.ccooy.expandablerecyclerview.demo.Frame
import com.ccooy.expandablerecyclerview.demo.TradeItem

class TradeView(var tradeItem: TradeItem) : Frame(TYPE) {
    companion object {
        const val TYPE = "trade_item"
    }
}