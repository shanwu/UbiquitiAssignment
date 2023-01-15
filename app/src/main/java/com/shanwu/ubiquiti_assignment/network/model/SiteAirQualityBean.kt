package com.shanwu.ubiquiti_assignment.network.model

import com.google.gson.annotations.SerializedName

class SiteAirQualityBean {
    @SerializedName("siteid")
    private val siteId = ""

    @SerializedName("sitename")
    private val siteName = ""

    @SerializedName("county")
    private val county = ""

    @SerializedName("status")
    private val status = ""

    @SerializedName("pm2.5")
    private val pmLevel = ""

    override fun toString(): String {
        return "site id: $siteId, site name: $siteName, county: $county, status: $status, pm2.5: $pmLevel"
    }

    fun getSiteId() = siteId
    fun getSiteName() = siteName
    fun getCounty() = county
    fun getStatus() = status
    fun getPmLevel() = pmLevel
}