package presentation.appsDeviceScreen.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import presentation.components.ContactData

@Composable
fun InstructionsDialog(contact: ContactData, secondsLeft: Int) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        title = {
            Text("${contact.name}משימה ראשונה, יש להתקשר ל")
        },
        text = {
            Text("$secondsLeft")
        }
    )
}

@Composable
fun CheckUnderstandingDialog(
    onYesClick: () -> Unit,
    onNoClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = onYesClick) {
                Text("כן")
            }
        },
        dismissButton = {
            Button(onClick = onNoClick) {
                Text("לא")
            }
        },
        title = {
            Text("האם ההוראה הייתה מובנת?")
        },
    )
}

@Composable
fun reminderDialog(onClick: () -> Unit, text: String) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        dismissButton = {
            Button(onClick = onClick) {
                Text("המשך")
            }
        },
        title = {
            Text(text)
        },
    )
}




