package presentation.detailedContact

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.Whatsapp
import dmt_proms.pass.generated.resources.black_messages
import dmt_proms.pass.generated.resources.black_phone
import dmt_proms.pass.generated.resources.black_video
import dmt_proms.pass.generated.resources.contact
import dmt_proms.pass.generated.resources.contact_details
import dmt_proms.pass.generated.resources.messages
import dmt_proms.pass.generated.resources.phone
import dmt_proms.pass.generated.resources.search
import dmt_proms.pass.generated.resources.video
import dmt_proms.pass.generated.resources.whatsapp
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.AppData
import presentation.components.ContactData
import presentation.components.InstructionsDialog
import presentation.components.circleWithPicture
import presentation.detailedContact.components.ContactDetailsSection

class DetailedContactScreen(private val contact: ContactData) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: DetailedContactViewModel = koinViewModel()

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

        BaseTabletScreen(
            title = stringResource(Res.string.contact),
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
                            text = contact.name.first().toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )
                    }
                    Text(
                        text = contact.name,
                        fontSize = 30.sp,
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

                    ContactDetailsSection(contact, viewModel)
                }
            }
        )

        LaunchedEffect(Unit) {
            viewModel.startCheckingIfUserDidSomething()
        }

        LaunchedEffect(nextScreen) {
            if (isNextScreen) {
                nextScreen?.let { screen ->
                    navigator?.push(screen)
                    viewModel.clearNextScreen()
                }
            }
        }


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

