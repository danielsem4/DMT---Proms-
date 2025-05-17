package presentation.contatcts

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.contacts
import dmt_proms.pass.generated.resources.plus
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import dmt_proms.pass.generated.resources.hana_cohen
import dmt_proms.pass.generated.resources.phone_number
import dmt_proms.pass.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.ContactData
import presentation.components.InstructionsDialog


class ContactsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ContactsViewModel = koinViewModel()
        val searchQuery by viewModel.searchQuery.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val showDialog by viewModel.showDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val contactsList = viewModel.contactsList.collectAsState().value
        val isPlaying by viewModel.isPlaying.collectAsState()
        val isCountdownActive by viewModel.isCountdownActive.collectAsState()
        val nextScreen by viewModel.nextScreen.collectAsState()
        val isNextScreen by viewModel.isNextScreen.collectAsState()

        val correctContact = stringResource(Res.string.hana_cohen)
        val phoneNumber = stringResource(Res.string.phone_number)

        BaseTabletScreen(
            title = stringResource(Res.string.contacts),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    SearchTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChanged(it) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize().pointerInput(Unit) {
                                    detectDragGestures { _, _ ->
                                        // viewModel.startScrollCountdown(correctContact)
                                    }
                                },
                            verticalArrangement = Arrangement.spacedBy(25.dp)
                        ) {
                            items(contactsList) { contact ->
                                ContactItem(contact = contact, viewModel = viewModel)
                            }

                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                                .size(50.dp)
                                .background(primaryColor, shape = RoundedCornerShape(10.dp))
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.plus),
                                contentDescription = stringResource(Res.string.plus),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            })

        LaunchedEffect(Unit) {
            viewModel.loadContacts(phoneNumber)
            viewModel.setCorrectContact(correctContact, phoneNumber)
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

    @Composable
    fun ContactItem(contact: ContactData, viewModel: ContactsViewModel) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().height(120.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp)).clickable {
                    viewModel.onContactClicked(contact)
                }
        ) {
            Box(
                modifier = Modifier.padding(20.dp)
                    .size(80.dp)
                    .background(color = primaryColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.first().toString(),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = contact.name,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = null,
        placeholder = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(Res.drawable.search),
                    contentDescription = stringResource(Res.string.search),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(Res.string.search), color = Color.Gray)
            }
        },
        modifier = modifier
            .width(200.dp)
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            textColor = Color.Black
        ),
        singleLine = true
    )
}