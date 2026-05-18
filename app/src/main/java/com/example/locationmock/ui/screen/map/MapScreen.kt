package com.example.locationmock.ui.screen.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.locationmock.core.location.LocationPoint
import com.example.locationmock.domain.usecase.StartMockUseCase
import com.example.locationmock.domain.usecase.StopMockUseCase

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val startMockUseCase = remember { StartMockUseCase(context) }
    val stopMockUseCase = remember { StopMockUseCase(context) }

    var latInput by remember { mutableStateOf("31.2304") }
    var lngInput by remember { mutableStateOf("121.4737") }
    var running by remember { mutableStateOf(false) }
    var statusText by remember { mutableStateOf("未启动") }

    Scaffold { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("地图主页（MVP）", style = MaterialTheme.typography.headlineSmall)
            Text("先用经纬度输入跑通流程；后续可替换为地图选点。")

            TextField(
                value = latInput,
                onValueChange = { latInput = it },
                label = { Text("纬度 Latitude") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = lngInput,
                onValueChange = { lngInput = it },
                label = { Text("经度 Longitude") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        val lat = latInput.toDoubleOrNull()
                        val lng = lngInput.toDoubleOrNull()
                        if (lat == null || lng == null) {
                            statusText = "经纬度格式错误，请输入数字"
                            return@Button
                        }

                        startMockUseCase(LocationPoint(latitude = lat, longitude = lng))
                        running = true
                        statusText = "模拟中：$lat, $lng"
                    }
                ) {
                    Text("开始模拟")
                }

                OutlinedButton(
                    onClick = {
                        stopMockUseCase()
                        running = false
                        statusText = "已停止，恢复真实定位"
                    },
                    enabled = running
                ) {
                    Text("停止模拟")
                }
            }

            Text("当前状态：$statusText")
        }
    }
}
