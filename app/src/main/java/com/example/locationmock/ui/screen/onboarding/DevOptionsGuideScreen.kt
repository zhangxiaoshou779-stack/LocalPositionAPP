package com.example.locationmock.ui.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
fun DevOptionsGuideScreen(
    onBackToChecklist: () -> Unit,
    onPassedNext: () -> Unit,
    onOpenBrandGuide: () -> Unit
) {
    val context = LocalContext.current
    val checker = remember { SystemSettingsChecker(context) }
    val navigator = remember { SystemSettingsNavigator(context) }
    var enabled by remember { mutableStateOf(checker.isDeveloperOptionsEnabled()) }

    Scaffold { inner ->
        Column(modifier = Modifier.fillMaxSize().padding(inner).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("开启开发者选项")
            Text(if (enabled) "当前状态：✅ 已开启" else "当前状态：❌ 未开启")
            Button(onClick = { navigator.openGeneralSettings() }) { Text("打开系统设置") }
            OutlinedButton(onClick = {
                enabled = checker.isDeveloperOptionsEnabled()
                if (enabled) onPassedNext()
            }) { Text("我已完成，立即检测") }
            OutlinedButton(onClick = onOpenBrandGuide) { Text("品牌教程") }
            OutlinedButton(onClick = onBackToChecklist) { Text("返回") }
        }
    }
}
