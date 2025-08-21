package org.example.hit.heal.oriantation.feature.presentation

import ToastMessage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * OrientationEndScreen is the screen displayed at the end of the orientation test.
 * Uploads the data to the server and notifies the user.
 */
class OrientationEndScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: OrientationTestViewModel = koinViewModel()
        val scope = rememberCoroutineScope()

        // UI state
        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }
        var isUploading by remember { mutableStateOf(false) }
        var isUploaded by remember { mutableStateOf(false) }

        val successMessage = stringResource(sentSuccessfully)
        val unexpectedErrorMessage = stringResource(unexpectedError)

        // Kick off upload automatically once (when the screen appears)
        LaunchedEffect(Unit) {
            sendToServer(
                viewModel = viewModel,
                onStart = { isUploading = true; isUploaded = false },
                onSuccess = {
                    isUploading = false
                    isUploaded = true
                    toastType = ToastType.Success
                    toastMessage = successMessage
                    // Navigate back after a short delay so the user sees the toast
                    scope.launch {
                        delay(2000)
                        navigator.popUntilRoot()
                    }
                },
                onFailure = { error ->
                    isUploading = false
                    isUploaded = false
                    toastType = ToastType.Error
                    toastMessage = error ?: unexpectedErrorMessage
                }
            )
        }

        BaseScreen(
            title = stringResource(summaryHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "7/7",
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Top content
                Column(
                    modifier = Modifier.align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(paddingXl)
                ) {
                    Text(stringResource(summaryHitberInstructions1), fontSize = LARGE)
                    Text(stringResource(summaryHitberInstructions2), fontSize = LARGE)
                    SuccessAnimation(modifier = Modifier.size(100.dp))

                    // Loading overlay while uploading
                    if (isUploading) {
                        Loading()
                    }
                }

                // Bottom actions + toast
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(80.dp)
                ) {
                    toastMessage?.let { msg ->
                        ToastMessage(
                            message = msg,
                            type = toastType,
                            alignUp = false,
                            onDismiss = { toastMessage = null }
                        )
                    }
                }
            }
        }
    }

    /**
     * Helper that wraps the VM call and wires UI callbacks for start/success/failure.
     * Replaces the old Snackbar-based flow with Toasts and proper loading state handling.
     */
    private fun sendToServer(
        viewModel: OrientationTestViewModel,
        onStart: () -> Unit,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        try {
            onStart()
            viewModel.sendToServer(
                onSuccess = { onSuccess() },
                onFailure = { error -> onFailure(error?.toString()) }
            )
        } catch (e: Exception) {
            onFailure(e.message)
        }
    }
}
