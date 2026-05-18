package com.example.locationmock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.locationmock.ui.navigation.AppNavHost
import com.example.locationmock.viewmodel.OnboardingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavHost(onboardingViewModel = OnboardingViewModel())
        }
    }
}
