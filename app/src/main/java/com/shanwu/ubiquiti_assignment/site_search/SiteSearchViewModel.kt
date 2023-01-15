package com.shanwu.ubiquiti_assignment.site_search

import androidx.lifecycle.SavedStateHandle
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
 * UiState for the Search screen
 */
data class SiteSearchUiState(
    val search: String = "",
    val filteredAirQualities: List<SiteAirQuality> = emptyList(),
    val isLoading: Boolean = false,
)

@HiltViewModel
class SiteSearchViewModel @Inject constructor(
    private val tasksRepository: AirQualityRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _searchKeyword = savedStateHandle.getStateFlow(SEARCH_KEYWORD, "")
    val uiState: StateFlow<SiteSearchUiState> =
        combine(tasksRepository.getAirQualityStream(), _searchKeyword) { aqInfo, keyword ->
            getMatchedInfo(aqInfo, keyword)
        }
            .map { Async.Success(it) }
            .onStart<Async<Pair<String, List<SiteAirQualityBean>>>> { emit(Async.Loading) }
            .map { dataAsync -> createSiteSearchUiState(dataAsync) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = SiteSearchUiState(isLoading = true)
            )


    private fun createSiteSearchUiState(dataAsync: Async<Pair<String, List<SiteAirQualityBean>>>): SiteSearchUiState =
        when (dataAsync) {
            Async.Loading -> {
                SiteSearchUiState(isLoading = true)
            }
            is Async.Success -> {
                val keyword = dataAsync.data.first
                val siteAirQualities = Converter.convertToSiteAirQuality(dataAsync.data.second)

                SiteSearchUiState(
                    search = keyword,
                    isLoading = false,
                    filteredAirQualities = siteAirQualities,
                )
            }
        }

    fun updateSearchKeyword(searchKey: String) {
        savedStateHandle[SEARCH_KEYWORD] = searchKey
    }

    private fun getMatchedInfo(
        tasksResult: NetworkResult<List<SiteAirQualityBean>>,
        keyword: String
    ): Pair<String, List<SiteAirQualityBean>> =
        if (keyword.isNotEmpty() && tasksResult is NetworkResult.Success) {
            Pair(keyword, getMatchedAirQualityList(tasksResult.data, keyword))
        } else {
            Pair(keyword, emptyList())
        }

    private fun getMatchedAirQualityList(
        list: List<SiteAirQualityBean>,
        keyword: String
    ): List<SiteAirQualityBean> {
        return list.filter {
            it.getSiteName().contains(keyword) || it.getCounty().contains(keyword)
        }
    }
}


const val SEARCH_KEYWORD = "SEARCH_KEYWORD"
