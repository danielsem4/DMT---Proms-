package presentation.dialScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import androidx.compose.ui.graphics.Color
import org.example.hit.heal.core.presentation.utils.ObserveLifecycle
import org.example.hit.heal.core.presentation.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.Icon.dialKeysIcon
import org.example.hit.heal.core.presentation.Resources.Icon.likeIcon
import org.example.hit.heal.core.presentation.Resources.String.dentistPass
import org.example.hit.heal.core.presentation.Resources.String.dentistPhoneNumber
import org.example.hit.heal.core.presentation.Resources.String.dialKeys
import org.example.hit.heal.core.presentation.Resources.String.dialer
import org.example.hit.heal.core.presentation.Resources.String.no
import org.example.hit.heal.core.presentation.Resources.String.understandingDialogText
import org.example.hit.heal.core.presentation.Resources.String.yes
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.dialogs.BaseYesNoDialog
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.MessageDialog
import presentation.dialScreen.components.DialDialog
import presentation.dialScreen.components.MissionStage

class DialScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val viewModel: DialScreenViewModel = koinViewModel()
        val correct = stringResource(dentistPass)
        val isDialogVisible by viewModel.isDialogVisible.collectAsState()
        val enteredNumber by viewModel.enteredNumber.collectAsState()
        val isPlaying by viewModel.isPlaying.collectAsState()
        val isNextScreen by viewModel.isNextScreen.collectAsState()
        val isCloseIconDialog by viewModel.isCloseIconDialog.collectAsState()
        val showUnderstandingDialog by viewModel.showUnderstandingDialog.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val showDialog by viewModel.showDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val isCountdownActive by viewModel.isCountdownActive.collectAsState()
        val nextScreen by viewModel.nextScreen.collectAsState()
        val currentMissionPass by viewModel.currentMissionPass.collectAsState()
        val correctNumber = stringResource(dentistPhoneNumber)

        BaseScreen(
            title = stringResource(dialer),
            config = ScreenConfig.TabletConfig,
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingLg)
                    ) {
                        // If user reached the third stage without dialing correctly, show only the correct contact
                        if (currentMissionPass == MissionStage.CorrectContactOnly) {
                            item {
                                Text(
                                    text = correct,
                                    fontSize = LARGE,
                                    color = Color.Black,
                                    modifier = Modifier.padding(paddingSm)
                                )
                            }
                        } else {
                            // Otherwise show full contacts list
                            items(viewModel.contacts) { contactResId ->
                                Text(
                                    text = stringResource(contactResId),
                                    fontSize = LARGE,
                                    color = Color.Black,
                                    modifier = Modifier.padding(paddingSm)
                                )
                            }
                        }
                    }

                    Button(
                        modifier = Modifier
                            .padding(paddingMd)
                            .align(Alignment.BottomEnd)
                            .size(80.dp)
                            .background(primaryColor, shape = RoundedCornerShape(radiusMd)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                        onClick = { viewModel.onUserClickedDialButton() }
                    ) {
                        Image(
                            painter = painterResource(dialKeysIcon),
                            contentDescription = stringResource(dialKeys),
                            modifier = Modifier.size(40.dp)
                        )
                    }

                }
            }
        )

        // Show the dial dialog when the user clicks the dial button or after 30 seconds of inactivity
        if (isDialogVisible) {
            DialDialog(
                enteredNumber = enteredNumber,
                onNumberClicked = {
                    viewModel.onNumberClicked(it)
                },
                onDial = { viewModel.checkCorrectNumber(correctNumber) },
                onDeleteClicked = { viewModel.deleteLastDigit() }
            )
        }

        LaunchedEffect(nextScreen) {
            if (isNextScreen) {
                nextScreen?.let { screen ->
                    navigator?.replace(screen)
                    viewModel.clearNextScreen()
                }
            }
        }

        // Indicates that the dial dialog should open when the mission stage changes to DialerOpened
        LaunchedEffect(currentMissionPass) {
            if (currentMissionPass == MissionStage.DialerOpened) {
                viewModel.toggleDialog()
            }
        }

        LaunchedEffect(Unit) {
            viewModel.startFirstCheck()
        }

        // Lifecycle observers to stop/play internal timers or checks
        ObserveLifecycle(
            onStop = {
                viewModel.stopAll()
            },
        )

        // Show a dialog asking if user understand instructions
        if (showUnderstandingDialog) {
            BaseYesNoDialog(
                onDismissRequest = { },
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
                        if (nextScreen != null) {
                            navigator?.replace(nextScreen!!)
                            viewModel.clearNextScreen()
                        } else {
                            viewModel.hideReminderDialog()
                        }
                    }

                )
            }
        }

        RegisterBackHandler(this) {
            navigator?.pop()
        }
    }
}
