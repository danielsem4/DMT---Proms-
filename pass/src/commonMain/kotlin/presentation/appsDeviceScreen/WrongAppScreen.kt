package presentation.appsDeviceScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.ObserveLifecycle
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.Icon.logoutIcon
import org.example.hit.heal.core.presentation.Resources.String.deviceAppTitle
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.wrongAppTitle
import org.example.hit.heal.core.presentation.Sizes.iconSizeXl
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.AppData
import presentation.components.MessageDialog

class WrongAppScreen(private val app: AppData) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: WrongAppViewModel = koinViewModel()
        val showDialog by viewModel.showDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val isPlaying by viewModel.isPlaying.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val backToApps by viewModel.backToApps.collectAsState()
        val isCountdownActive by viewModel.isCountdownActive.collectAsState()

        // Screen shown when the user selects the wrong app. Displays a message and offers navigation back to the app list.
        BaseScreen(
            title = stringResource(deviceAppTitle),
            config = ScreenConfig.TabletConfig,
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(logoutIcon),
                        contentDescription = stringResource(exit),
                        modifier = Modifier
                            .size(iconSizeXl)
                            .align(Alignment.TopEnd)
                            .clickable {
                                viewModel.setSecondTimeWrongApp()
                                navigator?.replace(AppDeviceScreen())
                            }
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(wrongAppTitle))
                            withStyle(style = SpanStyle(color = primaryColor)) {
                                append(stringResource(app.label))                        }
                        },
                        modifier = Modifier.align(Alignment.Center), fontSize = LARGE
                    )
                }
            }
        )

        LaunchedEffect(Unit) {
            viewModel.startCheckingIfUserDidSomething()
        }

        // Lifecycle observers to stop/play internal timers or checks
        ObserveLifecycle(
            onStop = {
                viewModel.stopAll()
            },
        )

        // Show dialog with instructions or the helpers dialog
        if (showDialog) {
            dialogAudioText?.let { (text, audio) ->
                val dialogText = stringResource(text)
                val dialogAudio = stringResource(audio)
                MessageDialog(
                    text = dialogText,
                    secondsLeft = countdown,
                    isPlaying = isPlaying,
                    shouldShowCloseIcon = true,
                    isCountdownActive = isCountdownActive,
                    onPlayAudio = { viewModel.onPlayAudioRequested(dialogAudio) },
                    onDismiss = {
                        viewModel.hideReminderDialog()
                        if (backToApps) {
                            viewModel.setBackToApps()
                            navigator?.replace(AppDeviceScreen())
                        }
                    }
                )
            }
        }

        RegisterBackHandler(this) {
            viewModel.resetAll()
            navigator?.pop()        }
    }
}


