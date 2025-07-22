package org.example.hit.heal.core.presentation.components

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.how_do_you_feel
import dmt_proms.ui.core.generated.resources.next
import dmt_proms.ui.core.generated.resources.previous
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.paddingXs
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.primaryColor

import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * BaseScreen is a reusable composable that provides a standard layout
 * for screens in the application.
 * all items are within a Column
 */

@Composable
fun BaseScreen(
    title: String,
    config: ScreenConfig = ScreenConfig.PhoneConfig, // Use ScreenConfig to define layout
    onPrevClick: (() -> Unit)? = null,
    onNextClick: (() -> Unit)? = null,
    prevButtonText: String = stringResource(Res.string.previous),
    nextButtonText: String = stringResource(Res.string.next),
    navigationIcon: @Composable (() -> Unit)? = null,
    topRightText: String? = null, // For tablet
    snackbarHostState: SnackbarHostState = SnackbarHostState(), // For tablet
    buttons: Array<TabletButton> = emptyArray(), // For tablet
    modifier: Modifier = Modifier,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    MaterialTheme {
        val statusBarValues = WindowInsets.safeDrawing.asPaddingValues()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(bottom = statusBarValues.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            Box(
                modifier = Modifier.fillMaxWidth().background(primaryColor)
                , contentAlignment = Alignment.Center
            ) {
                navigationIcon?.let {
                    Box(
                        modifier = Modifier.align(Alignment.CenterStart)
                            .padding(top = statusBarValues.calculateTopPadding())
                    ) {
                        it()
                    }
                }

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = EXTRA_MEDIUM,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.align(Alignment.Center)
                        .padding(top = statusBarValues.calculateTopPadding())
                )

                if (config.showTopRightText) {
                    topRightText?.let {
                        Text(
                            text = it,
                            color = Color.White,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.TopEnd)
                                .padding(end = 16.dp, top = statusBarValues.calculateTopPadding())
                        )
                    }
                }
            }

            // Dynamic Content
            Column(
                modifier = modifier.padding(config.contentPadding.dp).weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }

            // Bottom Navigation Bar
            if (config.showNavigationButtons &&
                (onPrevClick != null || onNextClick != null)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = paddingSm, vertical = paddingXs),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    onPrevClick?.let {
                       // RoundedButton(stringResource(Res.string.previous), onClick = it)
                        RoundedButton(text = prevButtonText, onClick = onPrevClick)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    onNextClick?.let {
                        RoundedButton(text = nextButtonText, onClick = onNextClick)
                       //RoundedButton(
                       //    text = stringResource(Res.string.next),
                       //    onClick = it
                       //)
                    }
                }
            } else if (!config.showNavigationButtons && buttons.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(
                        horizontal = config.topBarPaddingHorizontal.dp,
                        vertical = config.topBarPaddingVertical.dp
                    ), horizontalArrangement = config.buttonArrangement
                ) {
                    buttons.forEachIndexed { index, btn ->
                        RoundedButton(btn.text, Modifier, onClick = btn.onClick)
                        if (index < buttons.size - 1) {
                            Spacer(modifier = Modifier.width(config.buttonSpacing.dp))
                        }
                    }
                }
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

data class TabletButton(val text: String, val onClick: () -> Unit)


@Preview
@Composable
fun SamplePhoneScreen() {
    BaseScreen(
        title = "Sample Phone",
        config = ScreenConfig.PhoneConfig,
        onPrevClick = { /* Handle previous */ },
        onNextClick = { /* Handle next */ }) {
        Text(text = stringResource(Res.string.how_do_you_feel))
        TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
fun SampleTabletScreen() {
    BaseScreen(
        title = "Sample Tablet",
        config = ScreenConfig.TabletConfig,
        topRightText = "User Name",
        buttons = arrayOf(
            TabletButton("Button 1") { /* Button 1 click */ },
            TabletButton("Button 2") { /* Button 2 click */ })
    ) {
        Text(text = "This is tablet specific content.")
        // Add more tablet-specific UI elements here
    }
}