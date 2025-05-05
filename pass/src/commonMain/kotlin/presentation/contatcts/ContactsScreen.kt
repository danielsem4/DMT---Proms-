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
import androidx.compose.material.Button
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
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.plus
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import presentation.detailedContact.DetailedContactScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.search
import presentation.appsDeviceScreen.components.reminderDialog
import presentation.components.ContactData


class ContactsScreen(private val correctContact: ContactData) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ContactsViewModel = viewModel()
        val contacts by viewModel.contacts.collectAsState()
        val searchQuery by viewModel.searchQuery
        val showReminderDialog by viewModel.showReminderDialog.collectAsState()
        val dialogText by viewModel.dialogText.collectAsState()
        val shouldNavigate by viewModel.shouldNavigateAfterDialog.collectAsState()

        if (showReminderDialog) {
            reminderDialog(
                text = dialogText,
                onClick = {
                    viewModel.hideReminderDialog()
                    if (shouldNavigate) {
                        navigator?.push(DetailedContactScreen(correctContact))
                    }
                }
            )
        }

        LaunchedEffect(Unit) {
            viewModel.startReminderCountdownForDidNothing(correctContact)
        }

        BaseTabletScreen(
            title = "אנשי קשר",
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
                                        viewModel.startRepeatCountdown(correctContact)
                                    }
                                },
                            verticalArrangement = Arrangement.spacedBy(25.dp)
                        ) {
                            items(contacts) { contact ->
                                ContactItem(contact = contact, correctContact = correctContact, viewModel = viewModel)
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
                                contentDescription = "plus",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            })
    }

    @Composable
    fun ContactItem(contact: ContactData, correctContact: ContactData, viewModel: ContactsViewModel) {
        val navigator = LocalNavigator.current

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().height(120.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp)).clickable {
                    if(contact == correctContact){
                        navigator?.push(DetailedContactScreen(contact))

                    }
                    else{
                        viewModel.startReminderCountdownForWrongContact(correctContact)
                        viewModel.startReminderCountdownForDidNothing(correctContact)
                    }
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
                    contentDescription = "חיפוש",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("חיפוש", color = Color.Gray)
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
