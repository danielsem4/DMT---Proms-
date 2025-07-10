
package org.example.hit.heal.presentaion.screens
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
import com.example.new_memory_test.backgroundColor
import com.example.new_memory_test.primaryColor

import dmt_proms.composeapp.generated.resources.Res
import org.example.hit.heal.core.presentation.components.RoundedButton

import org.jetbrains.compose.resources.stringResource
 import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * BaseScreen is a reusable composable that provides a standard layout
 * for screens in the application.
 */

@Composable
fun BaseScreen(
    title: String,
    onPrevClick: (() -> Unit)? = null,
    onNextClick: (() -> Unit)? = null,
     prevButtonText: String = "Previous",
    nextButtonText: String = "Next",
     navigationIcon: @Composable (() -> Unit)? = null,
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
                    .padding(12.dp)
            ) {
                navigationIcon?.let {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = statusBarValues.calculateTopPadding())
                    ) {
                        it()
                    }
                }

                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = statusBarValues.calculateTopPadding())
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
                         RoundedButton(prevButtonText, Modifier, it)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    onNextClick?.let {
                        RoundedButton(nextButtonText, Modifier, it)
                         RoundedButton("previous", Modifier, it)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    onNextClick?.let {
                        RoundedButton("next", Modifier, it)
                     }
                }
            }
        }
    }
}


