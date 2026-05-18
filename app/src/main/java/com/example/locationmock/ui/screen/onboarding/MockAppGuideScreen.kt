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
fun MockAppGuideScreen(
    appDisplayName: String,
    onBackToChecklist: () -> Unit,
    onPassedNext: () -> Unit
) {
    val context = LocalContext.current
    val checker = remember { SystemSettingsChecker(context) }
    val navigator = remember { SystemSettingsNavigator(context) }
    var selected by remember { mutableStateOf(checker.isMockLocationAppSelected()) }

    Scaffold { inner ->
        Column(modifier = Modifier.fillMaxSize().padding(inner).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("选择模拟位置信息应用")
            Text("请设置为：$appDisplayName")
            Text(if (selected) "当前状态：✅ 已选择" else "当前状态：❌ 未选择")
            Button(onClick = { navigator.openDeveloperOptions() }) { Text("打开开发者选项") }
            OutlinedButton(onClick = {
                selected = checker.isMockLocationAppSelected()
                if (selected) onPassedNext()
            }) { Text("我已设置，立即检测") }
            OutlinedButton(onClick = onBackToChecklist) { Text("返回") }
        }
    }
}
