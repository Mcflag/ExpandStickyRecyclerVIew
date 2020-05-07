package cn.lcsw.lcpay.ui.traderecord

import cn.lcsw.lcpay.data.bean.response.TradeListItem
import cn.lcsw.lcpay.data.bean.response.TradeSummaryListItem
import com.chad.library.adapter.base.entity.SectionEntity

class TradeRecordSection: SectionEntity<TradeListItem> {

    lateinit var tradeSummaryListItem: TradeSummaryListItem
    var isLastOfDay: Boolean = false

    constructor(tradeSummaryListItem: TradeSummaryListItem): super(true, null){
        this.tradeSummaryListItem = tradeSummaryListItem
    }

    constructor(tradeListItem: TradeListItem, isLastOfDay: Boolean = false): super(tradeListItem){
        this.isLastOfDay = isLastOfDay
    }
}