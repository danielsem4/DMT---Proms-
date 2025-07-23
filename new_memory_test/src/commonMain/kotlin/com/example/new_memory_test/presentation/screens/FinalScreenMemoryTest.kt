package com.example.new_memory_test.presentation.screens
import ToastMessage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import core.utils.RegisterBackHandler
import dmt_proms.new_memory_test.generated.resources.Res
import kotlinx.coroutines.delay
import org.example.hit.heal.core.presentation.FontSize
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.end
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.sentSuccessfully
import org.example.hit.heal.core.presentation.Resources.String.thanksCoffe
import org.example.hit.heal.core.presentation.Resources.String.unexpectedError
import org.example.hit.heal.core.presentation.Sizes.elevationSm
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.Sizes.spacing4Xl
import org.example.hit.heal.core.presentation.Sizes.spacing8Xl
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingXxl
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
        val lifecycleOwner = LocalLifecycleOwner.current

        val uploadStatus by viewModel.uploadStatus.collectAsStateWithLifecycle()
        val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

        val successMessage = stringResource(sentSuccessfully)
        val unexpectedErrorMessage = stringResource(unexpectedError)

        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }


        LaunchedEffect(lifecycleOwner) {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uploadAllImages()
            }
        }


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

        BaseScreen(
            title = stringResource(end),
            config = ScreenConfig.TabletConfig,
            content = {
                toastMessage?.let { msg ->
                    ToastMessage(
                        message = msg,
                        type = toastType,
                        alignUp = false,
                        onDismiss = { toastMessage = null }
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(spacingXxl)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, shape = RoundedCornerShape(radiusMd))
                                .border(
                                    width = elevationSm,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(radiusMd)
                                )
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
                        }

                        // Убрали дублирующий SuccessAnimation
                        if (!isLoading) {
                            SuccessAnimation(modifier = Modifier.size(spacing4Xl))
                        }

                        if (isLoading) {
                            Loading()
                        }
                    }

                    RoundedButton(
                        text = stringResource(exit),
                        modifier = Modifier.align(Alignment.CenterHorizontally).width(200.dp),
                        onClick = {
                            navigator?.popUntilRoot()
                            viewModel.reset()
                        },
                        enabled = uploadStatus != null
                    )
                }
            }
        )

        RegisterBackHandler(this) {
            navigator?.popUntilRoot()
        }
    }
}