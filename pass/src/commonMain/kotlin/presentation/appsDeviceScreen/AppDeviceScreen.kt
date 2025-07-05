package presentation.appsDeviceScreen

import org.example.hit.heal.core.presentation.components.VerticalTabletBaseScreen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.ObserveLifecycle
import org.example.hit.heal.core.presentation.Resources.String.deviceAppsTitle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.CheckUnderstandingDialog
import presentation.components.InstructionsDialog
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
        val isCloseIconDialog by viewModel.isCloseIconDialog.collectAsState()
        val showUnderstandingDialog by viewModel.showUnderstandingDialog.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val showDialog by viewModel.showDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val isCountdownActive by viewModel.isCountdownActive.collectAsState()
        val nextScreen by viewModel.nextScreen.collectAsState()

        VerticalTabletBaseScreen(
            title = stringResource(deviceAppsTitle),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
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


        LaunchedEffect(nextScreen) {
            if (isNextScreen) {
                nextScreen?.let { screen ->
                    navigator?.push (screen)
                    viewModel.clearNextScreen()
                }
            }
        }

        ObserveLifecycle(
            onStop = {
                viewModel.stopAll()
                println("elapsed:onStop")
            },
            onStart = {
                viewModel.startCheckingIfUserDidSomething()
                println("elapsed:onStart")

            }
        )

        DisposableEffect(Unit) {
            onDispose {
                viewModel.stopAll()
            }
        }

        if (showUnderstandingDialog) {
            CheckUnderstandingDialog(
                onYesClick = {
                    viewModel.onUnderstandingConfirmed()
                },
                onNoClick = { viewModel.onUnderstandingDenied() }
            )
        }

        if (showDialog) {
            dialogAudioText?.let { (text, audio) ->
                val audioString = stringResource(audio)
                InstructionsDialog(
                    text = stringResource(text),
                    secondsLeft = countdown,
                    isPlaying = isPlaying,
                    shouldShowCloseIcon = isCloseIconDialog,
                    isCountdownActive = isCountdownActive,
                    onPlayAudio = { viewModel.onPlayAudioRequested(audioString) },
                    onDismiss = {
                        viewModel.hideReminderDialog()

                        nextScreen?.let { screen ->
                            navigator?.push (screen)
                            viewModel.clearNextScreen()
                        }
                    }
                )
            }
        }
    }
}
