package org.example.hit.heal.cdt.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.Colors.backgroundColor
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.core.presentation.components.RoundedButton

@Composable
fun TabletBaseScreen(
    title: String,
    topRightText: String? = null,
    content: @Composable ColumnScope.() -> Unit,
    snackbarHost: @Composable (() -> Unit)? = null,
    vararg buttons: TabletButton
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
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(top = statusBarValues.calculateTopPadding())
                )
                topRightText?.let {
                    Text(
                        text = it,
                        color = Color.White,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 16.dp, top = statusBarValues.calculateTopPadding())
                    )
                }
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
            if (buttons.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    buttons.forEachIndexed { index, btn ->
                        RoundedButton(btn.text, Modifier, btn.onClick)
                        if (index < buttons.size - 1) {
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
            }

            snackbarHost?.invoke()
        }
    }
}


data class TabletButton(val text: String, val onClick: () -> Unit)
