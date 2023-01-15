package com.shanwu.ubiquiti_assignment

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shanwu.ubiquiti_assignment.air_quality.AirQualityScreen
import com.shanwu.ubiquiti_assignment.site_search.SiteSearchScreen

@Composable
fun AirNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.HOMEPAGE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screens.HOMEPAGE) {
            AirQualityScreen(
                onSearch = {
                    navController.navigate(Screens.SITE_SEARCH) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screens.SITE_SEARCH) {
            SiteSearchScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

private object Screens {
    const val HOMEPAGE = "home_page"
    const val SITE_SEARCH = "search"
}