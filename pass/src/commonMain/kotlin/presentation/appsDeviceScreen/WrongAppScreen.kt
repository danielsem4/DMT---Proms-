package presentation.appsDeviceScreen

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.L
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.exit
import kotlinx.atomicfu.TraceBase.None.append
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.appsDeviceScreen.components.InstructionsDialog
import presentation.components.AppData
import presentation.contatcts.ContactsScreen

class WrongAppScreen(private val app: AppData) : Screen {

    @Composable
    override fun Content() {
        val viewModel: WrongAppViewModel = koinViewModel()
        val showDialog by viewModel.showDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val isPlaying by viewModel.isPlaying.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val backToApps by viewModel.backToApps.collectAsState()
        val isCountdownActive by viewModel.isCountdownActive.collectAsState()

        val navigator = LocalNavigator.current

        LaunchedEffect(Unit){
            viewModel.startCheckingIfUserDidSomething()
        }

        BaseTabletScreen(
            title = "אפליקציה",
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(Res.drawable.exit),
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.TopEnd)
                            .clickable {
                                viewModel.setSecondTimeWrongApp()
                                navigator?.push(AppDeviceScreen())
                            }
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("האפליקציה שנכנסת אליה היא: ")
                            withStyle(style = SpanStyle(color = primaryColor)) {
                                append(app.label)
                            }
                        },
                        modifier = Modifier.align(Alignment.Center), fontSize = 30.sp
                    )
                }
            }
        )

        if (showDialog) {
            dialogAudioText?.let { (text, audio) ->
                val dialogText = stringResource(text)
                val dialogAudio = stringResource(audio)
                InstructionsDialog(
                    text = dialogText,
                    secondsLeft = countdown,
                    isPlaying = isPlaying,
                    shouldShowCloseIcon = true,
                    isCountdownActive = isCountdownActive,
                    onPlayAudio = { viewModel.playAudio(dialogAudio) },
                    onDismiss = {
                        viewModel.hideReminderDialog()
                        if (backToApps) {
                            viewModel.setBackToApps()
                            navigator?.push(AppDeviceScreen())
                        }
                    }
                )
            }
        }
    }
}


