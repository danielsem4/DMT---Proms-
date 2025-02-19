package org.example.hit.heal.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.hit.heal.evaluations.presentaion.backgroundColor
import org.example.hit.heal.evaluations.presentaion.components.RoundedButton
import org.example.hit.heal.evaluations.presentaion.primaryColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BaseScreen(
    title: String,
    onPrevClick: (() -> Unit)? = null,
    onNextClick: (() -> Unit)? = null,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    MaterialTheme {

        val statusBarValues = WindowInsets.safeDrawing.asPaddingValues()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(bottom = statusBarValues.calculateBottomPadding())
        ) {
            // Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryColor)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = statusBarValues.calculateTopPadding())
                )
            }

            // Dynamic Content
            Column(
                modifier = Modifier.padding(8.dp).weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }

            // Bottom Navigation Bar
            if (onPrevClick != null || onNextClick != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    onPrevClick?.let {
                        RoundedButton("Previous", Modifier, it)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    onNextClick?.let {
                        RoundedButton("Next", Modifier, it)
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun SampleScreen() {
    BaseScreen(
        title = "Sample",
        onPrevClick = { /* Handle previous */ },
        onNextClick = { /* Handle next */ }
    ) {
        Text(text = "How do you feel?")
        TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
    }
}
