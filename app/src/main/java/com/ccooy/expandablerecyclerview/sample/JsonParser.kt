package com.ccooy.expandablerecyclerview.sample

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal object JsonParser {

    fun fromJsonSummary(json: String): List<HeadEntity> {
        return Gson().fromJson(json, object : TypeToken<ArrayList<HeadEntity>>() {}.type)
    }

    fun fromJsonTrade(json: String): List<ChildEntity> {
        return Gson().fromJson(json, object : TypeToken<ArrayList<ChildEntity>>() {}.type)
    }
}