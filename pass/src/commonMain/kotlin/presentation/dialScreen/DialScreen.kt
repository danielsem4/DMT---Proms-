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
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import androidx.compose.ui.graphics.Color
import core.utils.ObserveLifecycle
import core.utils.RegisterBackHandler
import kotlinx.coroutines.delay
import org.example.hit.heal.core.presentation.Resources.Icon.dialKeysIcon
import org.example.hit.heal.core.presentation.Resources.Icon.likeIcon
import org.example.hit.heal.core.presentation.Resources.String.dentistPass
import org.example.hit.heal.core.presentation.Resources.String.dentistPhoneNumber
import org.example.hit.heal.core.presentation.Resources.String.dialKeys
import org.example.hit.heal.core.presentation.Resources.String.dialer
import org.example.hit.heal.core.presentation.Resources.String.no
import org.example.hit.heal.core.presentation.Resources.String.understandingDialogText
import org.example.hit.heal.core.presentation.Resources.String.yes
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.BaseYesNoDialog
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.InstructionsDialog
import presentation.dialScreen.components.DialDialog

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
                            .padding(24.dp)
                    ) {
                        if (currentMissionPass == 3) {
                            item {
                                Text(
                                    text = correct,
                                    fontSize = 35.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        } else {
                            items(viewModel.contacts) { contactResId ->
                                Text(
                                    text = stringResource(contactResId),
                                    fontSize = 35.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }


                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
                            .size(80.dp)
                            .background(primaryColor, shape = RoundedCornerShape(10.dp)),
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

        if (isDialogVisible) {
            DialDialog(
                enteredNumber = enteredNumber,
                onNumberClicked = {
                    viewModel.onNumberClicked(it) },
                onDial = { viewModel.checkCorrectNumber(correctNumber) },
                onDeleteClicked = { viewModel.deleteLastDigit() }
            )
        }

        LaunchedEffect(nextScreen) {
            if (isNextScreen) {
                nextScreen?.let { screen ->
                    navigator?.push(screen)
                    viewModel.clearNextScreen()
                }
            }
        }

        LaunchedEffect(currentMissionPass) {
            if (currentMissionPass == 2) {
                viewModel.toggleDialog()
            }
        }

        ObserveLifecycle(
            onStop = {
                viewModel.stopAll()
            },
            onStart = {
                viewModel.startFirstCheck()
            }
        )

        if (showUnderstandingDialog) {
            LaunchedEffect(Unit) {
                delay(30_000)
                viewModel.onUnderstandingDidNothing()
            }

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
                        if (nextScreen != null) {
                            navigator?.push(nextScreen!!)
                            viewModel.clearNextScreen()
                        } else {
                            viewModel.hideReminderDialog()
                        }
                    }

                )
            }
        }

        RegisterBackHandler(this) {
            navigator?.popUntilRoot()        }
    }
}
