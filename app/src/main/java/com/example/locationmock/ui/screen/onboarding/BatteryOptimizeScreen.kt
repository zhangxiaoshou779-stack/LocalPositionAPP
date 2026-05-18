package com.example.locationmock.ui.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.locationmock.core.settings.SystemSettingsChecker
import com.example.locationmock.core.settings.SystemSettingsNavigator

@Composable
fun BatteryOptimizeScreen(
    onBackToChecklist: () -> Unit,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    val checker = remember { SystemSettingsChecker(context) }
    val navigator = remember { SystemSettingsNavigator(context) }

    var ignored by remember { mutableStateOf(checker.isIgnoringBatteryOptimizations()) }

    Scaffold { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("后台稳定建议", style = MaterialTheme.typography.headlineSmall)
            Text("为避免系统清理后台导致定位中断，建议关闭电池优化。")
            Text(
                if (ignored) "当前状态：✅ 已忽略电池优化" else "当前状态：⚠️ 未忽略（可继续）"
            )

            Button(onClick = {
                navigator.openBatteryOptimizationSettings()
            }) {
                Text("去设置电池优化")
            }

            OutlinedButton(onClick = {
                ignored = checker.isIgnoringBatteryOptimizations()
            }) {
                Text("我已设置，立即检测")
            }

            Button(onClick = onNext) {
                Text("继续")
            }

            OutlinedButton(onClick = onBackToChecklist) {
                Text("返回检查清单")
            }
        }
    }
}
