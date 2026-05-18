package com.example.locationmock.domain.usecase

import android.content.Context
import android.content.Intent
import com.example.locationmock.core.location.LocationPoint
import com.example.locationmock.service.MockLocationService

class StartMockUseCase(private val context: Context) {
    operator fun invoke(point: LocationPoint) {
        val intent = Intent(context, MockLocationService::class.java).apply {
            putExtra("lat", point.latitude)
            putExtra("lng", point.longitude)
            putExtra("accuracy", point.accuracy)
            putExtra("altitude", point.altitude)
        }
        context.startForegroundService(intent)
    }
}

class StopMockUseCase(private val context: Context) {
    operator fun invoke() {
        context.stopService(Intent(context, MockLocationService::class.java))
    }
}
