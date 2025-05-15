package presentation.appsDeviceScreen

import BaseTabletScreen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.appsDeviceScreen.components.CheckUnderstandingDialog
import presentation.appsDeviceScreen.components.InstructionsDialog
import presentation.appsDeviceScreen.components.getGridItems
import presentation.components.circleWithPicture

class AppDeviceScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: AppDeviceViewModel = koinViewModel()
        val navigator = LocalNavigator.current

        val items = getGridItems()
        val isPlaying by viewModel.isPlaying.collectAsState()
        val isCloseIconDialog by viewModel.isCloseIconDialog.collectAsState()
        val showUnderstandingDialog by viewModel.showUnderstandingDialog.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val showDialog by viewModel.showDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val isCountdownActive by viewModel.isCountdownActive.collectAsState()
        val nextScreen by viewModel.nextScreen.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.startCheckingIfUserDidSomething()
        }

        LaunchedEffect(nextScreen) {
            nextScreen?.let { screen ->
                navigator?.push(screen)
                viewModel.clearNextScreen()
            }
        }

        BaseTabletScreen(
            title = "אפליקציות המכשיר",
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

        if (showUnderstandingDialog) {
            CheckUnderstandingDialog(
                onYesClick = { viewModel.onUnderstandingConfirmed() },
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
                    onPlayAudio = { viewModel.playAudio(audioString) },
                    onDismiss = {
                        viewModel.hideReminderDialog()

                        nextScreen?.let { screen ->
                            navigator?.push(screen)
                            viewModel.clearNextScreen()
                        }
                    }
                )
            }
        }
    }
}
