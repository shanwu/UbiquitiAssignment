package com.shanwu.ubiquiti_assignment.network.model

import com.google.gson.annotations.SerializedName

class AirQualityInfoBean {
    @SerializedName("records")
    private val airQualityList = mutableListOf<SiteAirQualityBean>()

    override fun toString(): String {
        return airQualityList.toString()
    }

    fun getSiteAirQualityList() = airQualityList.toList()
}