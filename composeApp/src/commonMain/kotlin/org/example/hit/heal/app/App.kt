package org.example.hit.heal.app

import androidx.compose.runtime.Composable
import org.example.hit.heal.evaluations.presentaion.DynamicScrollScreen
import org.example.hit.heal.evaluations.presentaion.ReportExample
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    DynamicScrollScreen(
        content = ReportExample(),
        onPrev = {}, onNext = {}
    )
}