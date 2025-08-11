package presentation.appsDeviceScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.utils.ObserveLifecycle
import org.example.hit.heal.core.presentation.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources.Icon.likeIcon
import org.example.hit.heal.core.presentation.Resources.String.deviceAppsTitle
import org.example.hit.heal.core.presentation.Resources.String.no
import org.example.hit.heal.core.presentation.Resources.String.understandingDialogText
import org.example.hit.heal.core.presentation.Resources.String.yes
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.dialogs.BaseYesNoDialog
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.appsDeviceScreen.AppDeviceProgressCache.isCloseIconDialog
import presentation.components.MessageDialog
import presentation.components.AppData
import presentation.components.circleWithPicture

class AppDeviceScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: AppDeviceViewModel = koinViewModel()
        val navigator = LocalNavigator.current

        val items: List<AppData> = viewModel.items.map { item ->
            AppData(
                imageRes = item.imageRes,
                circleColor = item.circleColor,
                label = item.label
            )
        }

        val isPlaying by viewModel.isPlaying.collectAsState()
        val isNextScreen by viewModel.isNextScreen.collectAsState()
        val showUnderstandingDialog by viewModel.showUnderstandingDialog.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val showDialog by viewModel.showDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val isCountdownActive by viewModel.isCountdownActive.collectAsState()
        val nextScreen by viewModel.nextScreen.collectAsState()

        BaseScreen(
            title = stringResource(deviceAppsTitle),
            config = ScreenConfig.TabletConfig,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingMd)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(paddingSm),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        itemsIndexed(items) { _, item ->
                            circleWithPicture(item = item) {
                                viewModel.onAppClicked(item)
                            }
                        }
                    }
                }
            }
        )

        // Navigate to the next screen when the user didn't do anything 3 times for 15 seconds or clicked 3 times wrong app
        LaunchedEffect(nextScreen) {
            if (isNextScreen) {
                nextScreen?.let { screen ->
                    navigator?.replace(screen)
                    viewModel.clearNextScreen()
                }
            }
        }

        /// Lifecycle observers to stop/play internal timers or checks
        LaunchedEffect(Unit) {
            viewModel.startCheckingIfUserDidSomething()
        }

        ObserveLifecycle(
            onStop = {
                viewModel.stopAll()
            },
        )


        // Show a dialog asking if user didn't understand instructions
        if (showUnderstandingDialog) {
            BaseYesNoDialog(
                onDismissRequest = {  },
                title = stringResource(understandingDialogText),
                icon = likeIcon,
                message = "",
                confirmButtonText = stringResource(yes),
                confirmButtonColor = primaryColor,
                onConfirm = { viewModel.onUnderstandingConfirmed() },
                dismissButtonText = stringResource(no),
                dismissButtonColor = primaryColor,
                onDismissButtonClick = { viewModel.onUnderstandingDenied() }
            )
        }

        // Show dialog with instructions or the helpers dialog
        if (showDialog) {
            dialogAudioText?.let { (text, audio) ->
                val audioString = stringResource(audio)
                MessageDialog(
                    text = stringResource(text),
                    secondsLeft = countdown,
                    isPlaying = isPlaying,
                    shouldShowCloseIcon = isCloseIconDialog,
                    isCountdownActive = isCountdownActive,
                    onPlayAudio = { viewModel.onPlayAudioRequested(audioString) },
                    onDismiss = {
                        viewModel.hideReminderDialog()

                        nextScreen?.let { screen ->
                            navigator?.replace(screen)
                            viewModel.clearNextScreen()
                        }
                    }
                )
            }
        }

        RegisterBackHandler(this) {
            viewModel.resetAll()
            navigator?.pop()
        }
    }
}
