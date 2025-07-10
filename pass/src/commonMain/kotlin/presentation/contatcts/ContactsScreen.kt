package presentation.contatcts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.ObserveLifecycle
import org.jetbrains.compose.resources.painterResource
import org.example.hit.heal.core.presentation.Resources.Icon.plusIcon
import org.example.hit.heal.core.presentation.Resources.String.contacts
import org.example.hit.heal.core.presentation.Resources.String.hanaCohen
import org.example.hit.heal.core.presentation.Resources.String.phoneNumber
import org.example.hit.heal.core.presentation.Resources.String.plus
import org.example.hit.heal.core.presentation.Sizes.iconSizeMd
import org.example.hit.heal.core.presentation.Sizes.iconSizeXl
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.InstructionsDialog
import presentation.contatcts.components.ContactItem
import presentation.contatcts.components.SearchTextField


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
        val isScrolled by viewModel.isScrolled.collectAsState()

        val correctContact = stringResource(hanaCohen)
        val phoneNumber = stringResource(phoneNumber)

        val listState = rememberLazyListState()

        LaunchedEffect(listState.firstVisibleItemScrollOffset) {
            if (listState.isScrollInProgress && !isScrolled) {
                    viewModel.startScrolling()
            }
        }

        BaseScreen(
            title = stringResource(contacts),
            config = ScreenConfig.TabletConfig,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingMd),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    SearchTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChanged(it) }
                    )

                    Spacer(modifier = Modifier.height(paddingMd))

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = paddingMd)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = listState,
                            verticalArrangement = Arrangement.spacedBy(paddingLg)
                        ) {
                            items(contactsList) { contact ->
                                ContactItem(contact = contact, viewModel = viewModel)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(paddingMd)
                                .size(iconSizeXl)
                                .background(primaryColor, shape = RoundedCornerShape(radiusMd))
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(plusIcon),
                                contentDescription = stringResource(plus),
                                modifier = Modifier.size(iconSizeMd)
                            )
                        }
                    }
                }
            })

        LaunchedEffect(Unit) {
            viewModel.loadContacts(phoneNumber)
            viewModel.setCorrectContact(correctContact, phoneNumber)
        }

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



