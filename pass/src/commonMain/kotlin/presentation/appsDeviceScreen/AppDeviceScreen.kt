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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.hana_cohen
import dmt_proms.pass.generated.resources.phone_number
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.appsDeviceScreen.components.CheckUnderstandingDialog
import presentation.appsDeviceScreen.components.InstructionsDialog
import presentation.appsDeviceScreen.components.getGridItems
import presentation.appsDeviceScreen.components.reminderDialog
import presentation.components.ContactData
import presentation.components.circleWithPicture
import presentation.contatcts.ContactsScreen


class AppDeviceScreen : Screen {
    @Composable
    override fun Content() {
        val items = getGridItems()
        val navigator = LocalNavigator.current
        val viewModel: AppDeviceViewModel = koinViewModel()

        val showDialog by viewModel.showDialog.collectAsState()
        val showSecondDialog by viewModel.showSecondDialog.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val contact by viewModel.contact.collectAsState()
        val showReminderDialog by viewModel.showReminderDialog.collectAsState()
        val dialogText by viewModel.dialogText.collectAsState()
        val didNothingMain by viewModel.didNothingMain.collectAsState()
        val didNothingApp by viewModel.didNothingApp.collectAsState()

        val contactName = stringResource(Res.string.hana_cohen)
        val contactPhone = stringResource(Res.string.phone_number)

        println("contactName = $contactName")

        LaunchedEffect(Unit) {
            viewModel.setContact(contactName, contactPhone)
            viewModel.triggerDialogSequenceIfNeeded()

            viewModel.navigationEvent.collect { event ->
                when (event) {
                    is AppDeviceViewModel.NavigationEvent.ToContactsScreen -> {
                        viewModel.contact.value?.let { navigator?.push(ContactsScreen(it)) }
                    }
                    is AppDeviceViewModel.NavigationEvent.ToWrongAppScreen -> {
                        navigator?.push(WrongAppScreen(event.app))
                    }
                    else->{

                    }
                }
            }
        }

        if (showDialog && contact != null) {
            InstructionsDialog(contact = contact!!, secondsLeft = countdown)
        }

        if (showSecondDialog) {
            CheckUnderstandingDialog(
                onYesClick = { viewModel.onUnderstandingConfirmed() },
                onNoClick = { viewModel.onUnderstandingDenied() }
            )
        }

        if (showReminderDialog) {
            val shouldNavigateToContacts = didNothingMain == 3 || didNothingApp == 2

            reminderDialog(
                onClick = {
                    viewModel.hideReminderDialog()
                    if (shouldNavigateToContacts) {
                        viewModel.contact.value?.let { navigator?.push(ContactsScreen(it)) }
                    }

                },
                text = dialogText
            )
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
                                viewModel.onUserDidSomething()
                                viewModel.onAppClicked(item)
                            }
                        }
                    }
                }
            }
        )
    }
}


