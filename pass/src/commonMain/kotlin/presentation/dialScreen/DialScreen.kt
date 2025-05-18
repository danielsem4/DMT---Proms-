package presentation.dialScreen

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.delete_number
import dmt_proms.pass.generated.resources.dentist_pass
import dmt_proms.pass.generated.resources.dentist_phone_number
import dmt_proms.pass.generated.resources.dial
import dmt_proms.pass.generated.resources.dial_keys
import dmt_proms.pass.generated.resources.dialer
import dmt_proms.pass.generated.resources.phone
import dmt_proms.pass.generated.resources.white_phone
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.CheckUnderstandingDialog
import presentation.components.InstructionsDialog

class DialScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val viewModel: DialScreenViewModel = koinViewModel()
        val correct = stringResource(Res.string.dentist_pass)
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
        val correctNumber = stringResource(Res.string.dentist_phone_number)

        BaseTabletScreen(
            title = stringResource(Res.string.dialer),
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
                            painter = painterResource(Res.drawable.dial_keys),
                            contentDescription = stringResource(Res.string.dial_keys),
                            modifier = Modifier.size(40.dp)
                        )
                    }

                }
            }
        )


        if (isDialogVisible) {
            DialDialog(
                enteredNumber = enteredNumber,
                onNumberClicked = { viewModel.onNumberClicked(it) },
                onDial = { viewModel.checkCorrectNumber(correctNumber) },
                onDeleteClicked = { viewModel.deleteLastDigit() }
            )
        }

        LaunchedEffect(Unit) {
            viewModel.startFirstCheck()
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
                    onPlayAudio = { viewModel.onPlayAudioRequested(audioString) },
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

    @Composable
    fun DialDialog(
        enteredNumber: String,
        onNumberClicked: (String) -> Unit,
        onDial: () -> Unit,
        onDeleteClicked: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth().fillMaxHeight(0.5f)
                    .background(Color(0xFFD3D3D3)).align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().height(60.dp)
                        .border(2.dp, Color.Black, RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.delete_number),
                            contentDescription = stringResource(Res.string.delete_number),
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { onDeleteClicked() }
                        )
                        Text(
                            text = enteredNumber,
                            fontSize = 25.sp,
                            color = Color.Gray,
                        )
                    }
                }


                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val numbers = listOf(
                        listOf("3", "2", "1"),
                        listOf("6", "5", "4"),
                        listOf("9", "8", "7"),
                        listOf("#", "0", "*")
                    )
                    numbers.forEach { row ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            row.forEach { number ->
                                Button(
                                    onClick = { onNumberClicked(number) },
                                    modifier = Modifier.width(100.dp).height(70.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                                ) {
                                    Text(text = number, fontSize = 40.sp, color = Color.Black)
                                }
                            }
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onDial,
                        modifier = Modifier
                            .align(Alignment.BottomCenter).height(100.dp)
                            .width(160.dp)
                            .padding(20.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(Res.string.dial),
                                fontSize = 25.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(Res.drawable.white_phone),
                                contentDescription = stringResource(Res.string.phone),
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}
