package com.example.new_memory_test.presentation.screens
import ToastMessage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.end
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.sentSuccessfully
import org.example.hit.heal.core.presentation.Resources.String.thanksCoffe
import org.example.hit.heal.core.presentation.Resources.String.unexpectedError
import org.example.hit.heal.core.presentation.Sizes.paddingXl
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.Loading
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.core.presentation.utils.animations.SuccessAnimation
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class FinalScreenMemoryTest : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ViewModelMemoryTest = koinViewModel()

        val uploadStatus by viewModel.uploadStatus.collectAsStateWithLifecycle()
        val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

        val successMessage = stringResource(sentSuccessfully)
        val unexpectedErrorMessage = stringResource(unexpectedError)

        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }
        val isUploadFinished = uploadStatus != null

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
        LaunchedEffect(Unit) {
            viewModel.uploadAllImages ()
        }

        BaseScreen(
            title = stringResource(end),
            config = ScreenConfig.TabletConfig,
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
                            onClick = { viewModel.reset()
                                navigator?.popUntilRoot()
                                 },
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
                        Text(
                            text = stringResource(thanksCoffe),
                            color = primaryColor,
                            fontSize = LARGE,
                            fontWeight = FontWeight.Bold,
                            lineHeight = EXTRA_LARGE,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        SuccessAnimation(modifier = Modifier.size(100.dp))

                        if (isLoading) {
                            Loading()
                        }
                    }

                }
            }
        )

        RegisterBackHandler(this) {
            viewModel.reset()
            navigator?.pop()
        }
    }
}