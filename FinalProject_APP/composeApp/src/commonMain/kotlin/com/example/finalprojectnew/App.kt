package com.example.finalprojectnew

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.example.finalprojectnew.stage1.presentation.Stage1NavGraph
import com.example.finalprojectnew.stage2.presentation.navigation.Stage2NavGraph
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier

sealed class AppStage {
    data object Stage1 : AppStage()
    data object Stage2 : AppStage()
}

@Composable
fun App() {
    MaterialTheme {
        var stage by remember { mutableStateOf<AppStage>(AppStage.Stage1) }

        Box(Modifier.fillMaxSize()) {
            when (stage) {
                AppStage.Stage1 -> {
                    Stage1NavGraph(
                        onStage1Done = { stage = AppStage.Stage2 }
                    )
                }
                AppStage.Stage2 -> {
                    Stage2NavGraph()
                }
            }
        }
    }
}
