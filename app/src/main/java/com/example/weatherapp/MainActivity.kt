package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.modules.viewmodel.startpage.view.StartPage
import com.example.weatherapp.ui.viewmodel.main_dashboard.view.LocationsScreen
import com.example.weatherapp.modules.viewmodel.air_qualitydetect.view.AirQualityScreen
import com.example.weatherapp.modules.viewmodel.severe_warning.view.SevereWarningScreen
import com.example.weatherapp.modules.viewmodel.location.view.LocationScreen
import com.example.weatherapp.modules.viewmodel.location_premission.view.LocationPermissionScreen
import com.example.weatherapp.modules.viewmodel.setting.view.SettingScreen
import com.example.weatherapp.modules.viewmodel.setting.viewmodel.SettingViewModel
import com.example.weatherapp.modules.viewmodel.search.view.SearchScreen
import com.example.weatherapp.modules.viewmodel.interactive_radar.view.RadarScreen
import com.example.weatherapp.modules.viewmodel.location_comparision.view.ComparisionScreen
import com.example.weatherapp.modules.viewmodel.hourly_forecast.view.HourlyForecastScreen
import com.example.weatherapp.modules.viewmodel.weekly_forecast.view.WeeklyForecastScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme

sealed class Screen(val route: String) {
    object Start : Screen("start_screen")
    object Permission : Screen("permission_screen")
    object Locations : Screen("locations_screen")
    object AirQuality : Screen("air_quality_screen")
    object SevereWarning : Screen("severe_warning_screen")
    object LocationDetail : Screen("location_detail_screen/{cityName}/{lat}/{lon}") {
        fun createRoute(cityName: String, lat: Double, lon: Double) = 
            "location_detail_screen/$cityName/$lat/$lon"
    }
    object Setting : Screen("setting_screen")
    object Search : Screen("search_screen")
    object Radar : Screen("radar_screen")
    object Comparision : Screen("comparision_screen")
    object HourlyForecast : Screen("hourly_forecast_screen/{cityName}/{lat}/{lon}") {
        fun createRoute(cityName: String, lat: Double, lon: Double) = 
            "hourly_forecast_screen/$cityName/$lat/$lon"
    }
    object WeeklyForecast : Screen("weekly_forecast_screen/{cityName}/{lat}/{lon}") {
        fun createRoute(cityName: String, lat: Double, lon: Double) = 
            "weekly_forecast_screen/$cityName/$lat/$lon"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingViewModel: SettingViewModel = viewModel()
            val isDarkMode by settingViewModel.isDarkMode.collectAsState()
            
            WeatherAppTheme(darkTheme = isDarkMode) {
                WeatherNavigation(settingViewModel)
            }
        }
    }
}

@Composable
fun WeatherNavigation(settingViewModel: SettingViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {
        composable(Screen.Start.route) {
            StartPage(onGetStartedClick = { navController.navigate(Screen.Permission.route) })
        }

        composable(Screen.Permission.route) {
            LocationPermissionScreen(
                onAllowClick = {
                    navController.navigate(Screen.Locations.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                },
                onSkipClick = {
                    navController.navigate(Screen.Locations.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Locations.route) {
            LocationsScreen(
                onBackClick = { navController.popBackStack() },
                onSearchClick = { navController.navigate(Screen.Search.route) },
                onAirQualityClick = { navController.navigate(Screen.AirQuality.route) },
                onWarningClick = { navController.navigate(Screen.SevereWarning.route) },
                onSettingClick = { navController.navigate(Screen.Setting.route) },
                onRadarClick = { navController.navigate(Screen.Radar.route) },
                onCompareClick = { navController.navigate(Screen.Comparision.route) },
                onLocationClick = { name, lat, lon ->
                    navController.navigate(Screen.LocationDetail.createRoute(name, lat, lon))
                }
            )
        }

        composable(Screen.AirQuality.route) {
            AirQualityScreen(
                onBackClick = { navController.popBackStack() },
                onSettingClick = { navController.navigate(Screen.Setting.route) },
                onSearchClick = { navController.navigate(Screen.Search.route) },
                onRadarClick = { navController.navigate(Screen.Radar.route) },
                onHomeClick = { 
                    navController.navigate(Screen.Locations.route) {
                        popUpTo(Screen.Locations.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.SevereWarning.route) {
            SevereWarningScreen(onBackClick = { navController.popBackStack() })
        }

        composable(
            route = Screen.LocationDetail.route,
            arguments = listOf(
                navArgument("cityName") { type = NavType.StringType },
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lon") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: "London"
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 51.5074
            val lon = backStackEntry.arguments?.getFloat("lon")?.toDouble() ?: 0.1278
            
            LocationScreen(
                cityName = cityName,
                latitude = lat,
                longitude = lon,
                onBackClick = { navController.popBackStack() },
                onSearchClick = { navController.navigate(Screen.Search.route) },
                onRadarClick = { navController.navigate(Screen.Radar.route) },
                onHourlyClick = {
                    navController.navigate(Screen.HourlyForecast.createRoute(cityName, lat, lon))
                },
                onWeeklyClick = {
                    navController.navigate(Screen.WeeklyForecast.createRoute(cityName, lat, lon))
                }
            )
        }

        composable(Screen.Setting.route) {
            SettingScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = settingViewModel
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                onResultClick = { name, lat, lon ->
                    navController.navigate(Screen.LocationDetail.createRoute(name, lat, lon))
                }
            )
        }

        composable(Screen.Radar.route) {
            RadarScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Comparision.route) {
            ComparisionScreen(onBackClick = { navController.popBackStack() })
        }

        composable(
            route = Screen.HourlyForecast.route,
            arguments = listOf(
                navArgument("cityName") { type = NavType.StringType },
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lon") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: "London"
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 51.5074
            val lon = backStackEntry.arguments?.getFloat("lon")?.toDouble() ?: 0.1278
            HourlyForecastScreen(
                cityName = cityName,
                lat = lat,
                lon = lon,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.WeeklyForecast.route,
            arguments = listOf(
                navArgument("cityName") { type = NavType.StringType },
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lon") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: "London"
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 51.5074
            val lon = backStackEntry.arguments?.getFloat("lon")?.toDouble() ?: 0.1278
            WeeklyForecastScreen(
                cityName = cityName,
                lat = lat,
                lon = lon,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
