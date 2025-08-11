package org.example.hit.heal.hitber.presentation.summary

import ToastMessage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.utils.RegisterBackHandler
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.sentSuccessfully
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberInstructions1
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberInstructions2
import org.example.hit.heal.core.presentation.Resources.String.summaryHitberTitle
import org.example.hit.heal.core.presentation.Resources.String.unexpectedError
import org.example.hit.heal.core.presentation.Sizes.paddingXl
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.Loading
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.utils.animations.SuccessAnimation
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class SummaryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = koinViewModel()
        val uploadStatus by viewModel.uploadStatus.collectAsState()
        val isUploadFinished = uploadStatus != null
        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }
        val successMessage = stringResource(sentSuccessfully)
        val unexpectedErrorMessage = stringResource(unexpectedError)
        val capturedBitmap1 by viewModel.capturedBitmap1.collectAsState()
        val capturedBitmap2 by viewModel.capturedBitmap2.collectAsState()
        val capturedBitmap3 by viewModel.capturedBitmap3.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val currentDate = getCurrentFormattedDateTime()

        BaseScreen(
            title = stringResource(summaryHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "10/10",
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(80.dp),
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        RoundedButton(
                            text = stringResource(exit),
                            modifier = Modifier.width(200.dp),
                            onClick = { navigator?.pop() },
                            enabled = isUploadFinished
                        )

                        toastMessage?.let { msg ->
                            ToastMessage(
                                message = msg,
                                type = toastType,
                                alignUp = false,
                                onDismiss = { toastMessage = null }
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(paddingXl)
                    ) {
                        Text(stringResource(summaryHitberInstructions1), fontSize = LARGE)
                        Text(stringResource(summaryHitberInstructions2), fontSize = LARGE)
                        SuccessAnimation(modifier = Modifier.size(100.dp))

                        if (isLoading) {
                            Loading()
                        }
                    }

                }
            }
        )

        // Show toast messages when upload status changes
        LaunchedEffect(uploadStatus) {
            uploadStatus?.let { result ->
                result.onSuccess {
                    toastMessage = successMessage
                    toastType = ToastType.Success
                }.onFailure { error ->
                    toastMessage = error.message ?: unexpectedErrorMessage
                    toastType = ToastType.Warning
                }
            }
        }

        // Upload captured bitmaps
        LaunchedEffect(capturedBitmap1) {
            capturedBitmap1?.let {
                viewModel.uploadImage(it, currentDate, 6)
            }
        }

        LaunchedEffect(capturedBitmap2) {
            capturedBitmap2?.let {
                viewModel.uploadImage(it, currentDate, 7)
            }
        }

        LaunchedEffect(capturedBitmap3) {
            capturedBitmap3?.let {
                viewModel.uploadImage(it, currentDate, 10)
            }
        }

        RegisterBackHandler(this) {
            navigator?.pop()
        }
    }

}


