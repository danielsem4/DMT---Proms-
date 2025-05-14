package presentation.appsDeviceScreen

import BaseTabletScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.call_hana_cohen_pass
import dmt_proms.pass.generated.resources.call_to_hana_cohen_instruction_pass
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.appsDeviceScreen.components.CheckUnderstandingDialog
import presentation.appsDeviceScreen.components.InstructionsDialog
import presentation.appsDeviceScreen.components.getGridItems
import presentation.components.circleWithPicture
import presentation.contatcts.ContactsScreen


class AppDeviceScreen : Screen {
    @Composable
    override fun Content() {
        val items = getGridItems()
        val navigator = LocalNavigator.current
        val viewModel: AppDeviceViewModel = koinViewModel()

        val isPlaying by viewModel.isPlaying.collectAsState()
        val showDialog by viewModel.showDialog.collectAsState()
        val isSecondInstructions by viewModel.isSecondInstructions.collectAsState()
        val showUnderstandingDialog by viewModel.showUnderstandingDialog.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val showReminderDialog by viewModel.showReminderDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val isFinished by viewModel.isFinished.collectAsState()


        if (showDialog) {
            val audio = stringResource(Res.string.call_hana_cohen_pass)
            InstructionsDialog(
                text = stringResource(Res.string.call_to_hana_cohen_instruction_pass),
                secondsLeft = countdown,
                isPlaying = isPlaying,
                shouldShowCloseIcon = isSecondInstructions,
                onPlayAudio = { viewModel.playAudio(audio) },
                onDismiss = { }
            )
        }


        if (showUnderstandingDialog) {
            CheckUnderstandingDialog(
                onYesClick = { viewModel.onUnderstandingConfirmed() },
                onNoClick = { viewModel.onUnderstandingDenied() }
            )
        }

        if (showReminderDialog) {
            dialogAudioText?.let { (text, audio) ->
                val dialogText = stringResource(text)
                val dialogAudio = stringResource(audio)

                InstructionsDialog(
                    text = dialogText,
                    secondsLeft = countdown,
                    isPlaying = isPlaying,
                    shouldShowCloseIcon = true,
                    onPlayAudio = { viewModel.playAudio(dialogAudio) },
                    onDismiss = {
                        viewModel.hideReminderDialog()
                        if (isFinished) {
                            navigator?.push(ContactsScreen())

                        }
                    }
                )
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
                                if (isFinished) {
                                    navigator?.push(ContactsScreen())
                                }
                                else{
                                    navigator?.push(WrongAppScreen(item))
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}


