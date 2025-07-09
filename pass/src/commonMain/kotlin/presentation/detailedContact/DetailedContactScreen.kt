package presentation.detailedContact

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.ObserveLifecycle
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.contact
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.AppData
import presentation.components.ContactData
import presentation.components.InstructionsDialog
import presentation.components.circleWithPicture
import presentation.detailedContact.components.ContactDetailsSection

class DetailedContactScreen() : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: DetailedContactViewModel = koinViewModel()
        val correctContact = DetailedContactCache.lastContact ?: ContactData(
            name = "Unknown",
            phoneNumber = "000-0000000"
        )

        val contactChatData: List<AppData> = viewModel.items.map { item ->
            AppData(
                imageRes = item.imageRes,
                circleColor = item.circleColor,
                label = item.label
            )
        }

        val countdown by viewModel.countdown.collectAsState()
        val showDialog by viewModel.showDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val isPlaying by viewModel.isPlaying.collectAsState()
        val isCountdownActive by viewModel.isCountdownActive.collectAsState()
        val nextScreen by viewModel.nextScreen.collectAsState()
        val isNextScreen by viewModel.isNextScreen.collectAsState()

        BaseScreen(
            title = stringResource(contact),
            config = ScreenConfig.TabletConfig,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .border(
                                width = 6.dp,
                                color = primaryColor,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = correctContact.name.first().toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = EXTRA_LARGE
                        )
                    }
                    Text(
                        text = correctContact.name,
                        fontSize = LARGE,
                        fontWeight = FontWeight.Bold,
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        contactChatData.forEach { item ->
                            circleWithPicture(item) { viewModel.onUserClicked(item.label) }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    ContactDetailsSection(correctContact, viewModel)
                }
            }
        )


        LaunchedEffect(nextScreen) {
            if (isNextScreen) {
                nextScreen?.let { screen ->
                    navigator?.replace(screen)
                    viewModel.clearNextScreen()
                }
            }
        }

        ObserveLifecycle(
            onStop = {
                viewModel.stopAll()
            },
            onStart = {
                viewModel.startCheckingIfUserDidSomething()
            }
        )


        if (showDialog) {
            dialogAudioText?.let { (text, audio) ->
                val audioString = stringResource(audio)
                InstructionsDialog(
                    text = stringResource(text),
                    secondsLeft = countdown,
                    isPlaying = isPlaying,
                    shouldShowCloseIcon = true,
                    isCountdownActive = isCountdownActive,
                    onPlayAudio = { viewModel.onPlayAudioRequested(audioString) },
                    onDismiss = {
                        viewModel.hideReminderDialog()
                    }
                )
            }
        }
    }
}

