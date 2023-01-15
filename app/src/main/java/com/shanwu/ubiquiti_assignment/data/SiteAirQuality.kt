package com.shanwu.ubiquiti_assignment.data

data class SiteAirQuality(
    val siteId: Int = 0,
    val siteName: String = "",
    val county: String = "",
    val pmLevel: Int = 0,
    val status: String = "",
)