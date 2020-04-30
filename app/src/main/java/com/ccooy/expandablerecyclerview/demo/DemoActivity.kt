package com.ccooy.expandablerecyclerview.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ccooy.expandablerecyclerview.R
import com.ccooy.expandablerecyclerview.demo.content.*
import com.drakeet.multitype.MultiTypeAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

    private lateinit var adapter: MultiTypeAdapter
    private lateinit var items: MutableList<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        adapter = MultiTypeAdapter()

        adapter.register(FrameItem::class).to(
            SummaryViewBinder(),
            DiscountViewBinder(),
            TradeViewBinder()
        ).withLinker { _, item ->
            when (item.content) {
                is SummaryView -> 0
                is DiscountView -> 1
                is TradeView -> 2
                else -> 2
            }
        }

        recycler_view.adapter = adapter

        items = ArrayList()

//        val tradeItem = TradeItem("云闪付", "39")
//        val discountItem = DiscountItem("1919-05-05","22","77")
//        for (i in 0..2) {
//            items.add(FrameItem(TradeView(tradeItem)))
//            items.add(FrameItem(DiscountView(discountItem)))
//        }
        adapter.items = items
        adapter.notifyDataSetChanged()

        loadRemoteDataSummary(0)
        loadRemoteDataList(1)
    }

    fun openListener(index: Int){
        Log.e("cccccc", "index:"+index)
        loadRemoteDataList(index+1)
    }

    fun closeListener(index: Int, count: Int){
        Log.e("cccccc", "index:"+index+";count:"+count)
        removeItems(index, count)
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

    private fun removeItems(index: Int, count: Int){
        items = ArrayList(items)
        for (i in 0 until count){
            items.removeAt(index+1)
        }
        Log.e("cccccc", Gson().toJson(items))
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    private fun loadRemoteDataSummary(index: Int) {
        val summaryList = JsonParser.fromJsonSummary(JSON_FROM_SERVICE_SUMMARY)
        var tempList = ArrayList<FrameItem>()
        items = ArrayList(items)
        tempList.add(FrameItem(SummaryView(summaryList[0], true, 5, ::openListener, ::closeListener)))
        for(i in 1 until summaryList.size){
            tempList.add(FrameItem(SummaryView(summaryList[i], false, 5, ::openListener, ::closeListener)))
        }
        items.addAll(index, tempList)
        Log.e("cccccc", Gson().toJson(items))
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    private fun loadRemoteDataList(index: Int) {
        val tradeList = JsonParser.fromJsonTrade(JSON_FROM_SERVICE_TRADE)
        var tempList = ArrayList<FrameItem>()
        items = ArrayList(items)
        for(item in tradeList){
            tempList.add(FrameItem(TradeView(item)))
        }
        items.addAll(index, tempList)
        Log.e("cccccc", Gson().toJson(items))
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    companion object {
        private const val JSON_FROM_SERVICE_SUMMARY = """[
            {
                "time":"2020-04-24",
                "count":"33",
                "discount":"90"
            },
            {
                "time":"2020-04-23",
                "count":"1",
                "discount":"90"
            },
            {
                "time":"2020-04-22",
                "count":"1",
                "discount":"90"
            },
            {
                "time":"2020-04-21",
                "count":"1",
                "discount":"90"
            },
            {
                "time":"2020-04-20",
                "count":"1",
                "discount":"90"
            }
        ]"""

        private const val JSON_FROM_SERVICE_TRADE = """[
            {
                "type":"支付宝",
                "total":"20.00"
            },
            {
                "type":"支付宝",
                "total":"19.00"
            },
            {
                "type":"微信",
                "total":"20.00"
            },
            {
                "type":"支付宝",
                "total":"18.00"
            },
            {
                "type":"支付宝",
                "total":"21.00"
            }
        ]"""
    }
}