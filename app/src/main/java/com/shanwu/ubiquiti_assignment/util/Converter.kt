package com.shanwu.ubiquiti_assignment.util

import android.util.Log
import com.shanwu.ubiquiti_assignment.data.SiteAirQuality
import com.shanwu.ubiquiti_assignment.network.model.SiteAirQualityBean

object Converter {
    fun convertToSiteAirQuality(beanList: List<SiteAirQualityBean>): List<SiteAirQuality> {
        val res = mutableListOf<SiteAirQuality>()
        for (bean in beanList) {
            try {
                val siteAirInfo = SiteAirQuality(
                    siteId = bean.getSiteId().toInt(),
                    county = bean.getCounty(),
                    status = bean.getStatus(),
                    siteName = bean.getSiteName(),
                    pmLevel = bean.getPmLevel().toInt()
                )
                res.add(siteAirInfo)
            } catch (e: Exception) {
                Log.w("guang", "invalid air info: $bean")
                continue
            }
        }
        return res
    }
}