package com.shanwu.ubiquiti_assignment.data.source

import com.shanwu.ubiquiti_assignment.data.NetworkResult
import com.shanwu.ubiquiti_assignment.data.SiteAirQuality
import com.shanwu.ubiquiti_assignment.network.model.SiteAirQualityBean
import kotlinx.coroutines.flow.Flow

interface AirQualityRepository {
    fun getAirQualityStream(): Flow<NetworkResult<List<SiteAirQualityBean>>>

    suspend fun refreshAirQualityInfo()
}