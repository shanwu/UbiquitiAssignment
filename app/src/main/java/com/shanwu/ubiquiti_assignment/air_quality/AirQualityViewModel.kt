package com.shanwu.ubiquiti_assignment.air_quality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanwu.ubiquiti_assignment.data.NetworkResult
import com.shanwu.ubiquiti_assignment.data.SiteAirQuality
import com.shanwu.ubiquiti_assignment.data.source.AirQualityRepository
import com.shanwu.ubiquiti_assignment.network.model.SiteAirQualityBean
import com.shanwu.ubiquiti_assignment.util.Async
import com.shanwu.ubiquiti_assignment.util.Converter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * UiState for the home screen
 */
data class AirQualityUiState(
    val bannerAirQualities: List<SiteAirQuality> = emptyList(),
    val listAirQualities: List<SiteAirQuality> = emptyList(),
    val isLoading: Boolean = false,
)

const val PM_LEVEL_THRESHOLD_VALUE = 10

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    private val tasksRepository: AirQualityRepository,
) : ViewModel() {
    val uiState: StateFlow<AirQualityUiState> =
        tasksRepository.getAirQualityStream()
            .map { Async.Success(it) }
            .onStart<Async<NetworkResult<List<SiteAirQualityBean>>>> { emit(Async.Loading) }
            .map { dataAsync -> createAirQualityUiState(dataAsync) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = AirQualityUiState(isLoading = true)
            )

    private fun createAirQualityUiState(taskLoad: Async<NetworkResult<List<SiteAirQualityBean>>>): AirQualityUiState =
        when (taskLoad) {
            Async.Loading -> {
                AirQualityUiState(isLoading = true)
            }
            is Async.Success -> {
                when (val result = taskLoad.data) {
                    is NetworkResult.Success -> {
                        val siteAirQualities = Converter.convertToSiteAirQuality(result.data)
                        AirQualityUiState(
                            isLoading = false,
                            listAirQualities = siteAirQualities.filter { it.pmLevel > PM_LEVEL_THRESHOLD_VALUE },
                            bannerAirQualities = siteAirQualities.filter { it.pmLevel <= PM_LEVEL_THRESHOLD_VALUE }
                        )
                    }
                    else -> {
                        AirQualityUiState(isLoading = false)
                    }
                }
            }
        }
}
