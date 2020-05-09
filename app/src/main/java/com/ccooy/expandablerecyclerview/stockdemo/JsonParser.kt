package com.ccooy.expandablerecyclerview.stockdemo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal object JsonParser {

    fun fromJsonSummary(json: String): List<SummaryEntity> {
        return Gson().fromJson(json, object : TypeToken<ArrayList<SummaryEntity>>() {}.type)
    }

    fun fromJsonTrade(json: String): List<DailyRecordEntity> {
        return Gson().fromJson(json, object : TypeToken<ArrayList<DailyRecordEntity>>() {}.type)
    }
}