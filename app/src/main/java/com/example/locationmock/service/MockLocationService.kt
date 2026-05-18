package com.example.locationmock.service

import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MockLocationService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var loopJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(1001, ServiceNotification.create(this))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val lat = intent?.getDoubleExtra("lat", 0.0) ?: 0.0
        val lng = intent?.getDoubleExtra("lng", 0.0) ?: 0.0
        val accuracy = intent?.getFloatExtra("accuracy", 5f) ?: 5f
        val altitude = intent?.getDoubleExtra("altitude", 0.0) ?: 0.0

        startMockLoop(lat, lng, accuracy, altitude)
        return START_STICKY
    }

    private fun startMockLoop(lat: Double, lng: Double, accuracy: Float, altitude: Double) {
        loopJob?.cancel()
        loopJob = serviceScope.launch {
            val lm = getSystemService(LOCATION_SERVICE) as LocationManager
            while (isActive) {
                try {
                    val mock = Location(LocationManager.GPS_PROVIDER).apply {
                        latitude = lat
                        longitude = lng
                        this.altitude = altitude
                        this.accuracy = accuracy
                        time = System.currentTimeMillis()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            elapsedRealtimeNanos = System.nanoTime()
                        }
                    }
                    lm.setTestProviderLocation(LocationManager.GPS_PROVIDER, mock)
                } catch (_: Exception) {
                }
                delay(1000)
            }
        }
    }

    override fun onDestroy() {
        loopJob?.cancel()
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
