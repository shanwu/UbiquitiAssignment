package com.shanwu.ubiquiti_assignment.data.source

import com.shanwu.ubiquiti_assignment.data.NetworkResult
import com.shanwu.ubiquiti_assignment.data.SiteAirQuality
import com.shanwu.ubiquiti_assignment.network.model.AirQualityInfoBean
import com.shanwu.ubiquiti_assignment.network.model.SiteAirQualityBean
import kotlinx.coroutines.flow.Flow

interface AirQualityDataSource {
    suspend fun getAirQuality(): NetworkResult<List<SiteAirQualityBean>>

    suspend fun refreshAirQualityData()

    fun getAirQualityStream(): Flow<NetworkResult<List<SiteAirQualityBean>>>
}
