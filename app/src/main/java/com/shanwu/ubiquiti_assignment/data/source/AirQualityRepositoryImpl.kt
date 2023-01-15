package com.shanwu.ubiquiti_assignment.data.source

import com.shanwu.ubiquiti_assignment.data.NetworkResult
import com.shanwu.ubiquiti_assignment.data.SiteAirQuality
import com.shanwu.ubiquiti_assignment.network.model.SiteAirQualityBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class AirQualityRepositoryImpl(
    private val tasksRemoteDataSource: AirQualityDataSource,
) : AirQualityRepository {

    override fun getAirQualityStream(): Flow<NetworkResult<List<SiteAirQualityBean>>> {
        return tasksRemoteDataSource.getAirQualityStream()
    }

    override suspend fun refreshAirQualityInfo() {
        val airQualityTask = tasksRemoteDataSource.getAirQuality()
        if (airQualityTask is NetworkResult.Success) {
            // store data to local db
        } else if (airQualityTask is NetworkResult.Error) {
            throw airQualityTask.exception
        }
    }
}