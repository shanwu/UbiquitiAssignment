package com.shanwu.ubiquiti_assignment.air_quality

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shanwu.ubiquiti_assignment.R
import com.shanwu.ubiquiti_assignment.data.SiteAirQuality
import com.shanwu.ubiquiti_assignment.ui.theme.UbiquitiAssignmentTheme
import com.shanwu.ubiquiti_assignment.util.ListViewItems

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AirQualityScreen(
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AirQualityViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UbiquitiAssignmentTheme {
        AirQualityContent(
            modifier = modifier,
            onSearch = onSearch,
            bannerList = uiState.bannerAirQualities,
            itemsList = uiState.listAirQualities
        )
    }
}

@Composable
fun AirQualityContent(
    modifier: Modifier = Modifier,
    onSearch: () -> Unit,
    bannerList: List<SiteAirQuality>,
    itemsList: List<SiteAirQuality>
) {
    Column {
        AddAirQualityTopAppBar(onSearch = onSearch)
        AddBannerItems(modifier = modifier, list = bannerList)
        Divider()
        ListViewItems(modifier = modifier, list = itemsList)
    }
}

@Composable
fun AddBannerItems(
    modifier: Modifier = Modifier,
    list: List<SiteAirQuality>
) {
    LazyRow(modifier = modifier) {
        items(list) {
            BannerCardItem(it)
        }
    }
}



@Composable
fun AddAirQualityTopAppBar(
    onSearch: () -> Unit
) {
    TopAppBar(
        elevation = 4.dp,
        title = {
            Text(text = stringResource(id = R.string.title))
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            IconButton(onClick = onSearch) {
                Icon(Icons.Filled.Search, null, tint = Color.White)
            }
        }
    )
}


@Composable
fun BannerCardItem(aq: SiteAirQuality) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable(enabled = false, onClick = {}),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row {
                Text(
                    text = aq.siteId.toString(),
                    modifier = Modifier.width(35.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = aq.siteName,
                    modifier = Modifier
                        .width(80.dp)
                        .background(Color.White),
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = aq.pmLevel.toString(),
                    modifier = Modifier
                        .width(35.dp)
                        .wrapContentWidth(Alignment.End),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row {
                Text(
                    text = aq.county,
                    modifier = Modifier
                        .width(75.dp)
                        .wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = aq.status,
                    modifier = Modifier
                        .width(75.dp)
                        .wrapContentWidth(Alignment.End)
                )
            }
        }
    }
}