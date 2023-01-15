package com.shanwu.ubiquiti_assignment.data.source.remote

import com.shanwu.ubiquiti_assignment.data.NetworkResult
import com.shanwu.ubiquiti_assignment.data.SiteAirQuality
import com.shanwu.ubiquiti_assignment.data.source.AirQualityDataSource
import com.shanwu.ubiquiti_assignment.network.ApiService
import com.shanwu.ubiquiti_assignment.network.model.AirQualityInfoBean
import com.shanwu.ubiquiti_assignment.network.model.SiteAirQualityBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AirQualityRemoteDataSource internal constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AirQualityDataSource {
    private val observableAirQuality = MutableStateFlow(runBlocking { getAirQuality() })

    override suspend fun refreshAirQualityData() {
        observableAirQuality.value = getAirQuality()
    }

    override fun getAirQualityStream(): Flow<NetworkResult<List<SiteAirQualityBean>>> {
        return observableAirQuality
    }

    override suspend fun getAirQuality(): NetworkResult<List<SiteAirQualityBean>> =
        withContext(ioDispatcher) {
            try {
                val result = ApiService.create().getAirQuality().execute()
                if (result.isSuccessful) {
                    val airInfoBean: AirQualityInfoBean? = result.body()
                    val beanList = airInfoBean?.getSiteAirQualityList()
                    if(beanList == null || beanList.isEmpty()) throw Exception("Air Quality info is null")
                    return@withContext NetworkResult.Success(beanList)
                }
                return@withContext NetworkResult.Error(Exception("result is not successful"))
            } catch (e: Exception) {
                return@withContext NetworkResult.Error(e)
            }
        }
}