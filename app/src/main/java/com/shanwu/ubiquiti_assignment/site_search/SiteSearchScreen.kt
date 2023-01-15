package com.shanwu.ubiquiti_assignment.site_search

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shanwu.ubiquiti_assignment.R
import com.shanwu.ubiquiti_assignment.ui.theme.UbiquitiAssignmentTheme
import com.shanwu.ubiquiti_assignment.util.ListViewItems

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SiteSearchScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: SiteSearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val matchedSiteAirQuality = uiState.filteredAirQualities
    UbiquitiAssignmentTheme {
        Column(modifier = modifier) {
            AddSearchBar(onBack) { searchKey -> viewModel.updateSearchKeyword(searchKey) }
            Divider()
            if (matchedSiteAirQuality.isEmpty()) {
                val keyword = uiState.search
                val msg = if (keyword.isNotEmpty()) {
                    stringResource(
                        id = R.string.not_found_msg,
                        keyword
                    )
                } else {
                    stringResource(
                        id = R.string.input_guide_msg,
                    )
                }
                AddGuidePage(modifier = modifier, message = msg)
            } else {
                ListViewItems(modifier = modifier, list = matchedSiteAirQuality)
            }
        }
    }
}

@Composable
fun AddGuidePage(modifier: Modifier = Modifier, message: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = message, textAlign = TextAlign.Center)
    }
}

@Composable
fun AddSearchBar(
    onBack: () -> Unit = {},
    onSearch: (String) -> Unit = {},
) {
    var text by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Filled.ArrowBack, null, tint = Color.Black)
        }
        TextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            placeholder = { Text(text = stringResource(id = R.string.input_hint)) },

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = Color.Gray
            ),
        )
    }

}
