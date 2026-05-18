package com.example.locationmock.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.locationmock.ui.screen.map.MapScreen
import com.example.locationmock.ui.screen.onboarding.BatteryOptimizeScreen
import com.example.locationmock.ui.screen.onboarding.DevOptionsGuideScreen
import com.example.locationmock.ui.screen.onboarding.MockAppGuideScreen
import com.example.locationmock.ui.screen.onboarding.PermissionScreen
import com.example.locationmock.viewmodel.OnboardingViewModel

@Composable
fun AppNavHost(
    onboardingViewModel: OnboardingViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Routes.PERMISSION) {
        composable(Routes.PERMISSION) {
            PermissionScreen(
                onBackToChecklist = { navController.popBackStack() },
                onGrantedNext = { navController.navigate(Routes.DEV_OPTIONS) }
            )
        }

        composable(Routes.DEV_OPTIONS) {
            DevOptionsGuideScreen(
                onBackToChecklist = { navController.popBackStack() },
                onPassedNext = { navController.navigate(Routes.MOCK_APP) },
                onOpenBrandGuide = {}
            )
        }

        composable(Routes.MOCK_APP) {
            MockAppGuideScreen(
                appDisplayName = "定位助手",
                onBackToChecklist = { navController.popBackStack() },
                onPassedNext = { navController.navigate(Routes.BATTERY) }
            )
        }

        composable(Routes.BATTERY) {
            BatteryOptimizeScreen(
                onBackToChecklist = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.MAP) }
            )
        }

        composable(Routes.MAP) {
            MapScreen()
        }
    }
}
