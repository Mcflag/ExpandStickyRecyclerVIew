package com.ccooy.expandablerecyclerview.demo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal object JsonParser {

    fun fromJsonSummary(json: String): List<SummaryItem> {
        return Gson().fromJson(json, object : TypeToken<ArrayList<SummaryItem>>() {}.type)
    }

    fun fromJsonTrade(json: String): List<TradeItem> {
        return Gson().fromJson(json, object : TypeToken<ArrayList<TradeItem>>() {}.type)
    }

    fun fromJsonList(json: String): List<TradeItem> {
        return Gson().fromJson(json, object : TypeToken<ArrayList<TradeItem>>() {}.type)
    }
}