package presentation.appsDeviceScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.calculator
import dmt_proms.pass.generated.resources.camera
import dmt_proms.pass.generated.resources.clock
import dmt_proms.pass.generated.resources.contacts
import dmt_proms.pass.generated.resources.documents
import dmt_proms.pass.generated.resources.email
import dmt_proms.pass.generated.resources.purse
import dmt_proms.pass.generated.resources.settings
import dmt_proms.pass.generated.resources.store
import dmt_proms.pass.generated.resources.weather
import dmt_proms.pass.generated.resources.white_messages
import dmt_proms.pass.generated.resources.white_phone
import org.example.hit.heal.core.presentation.Colors.primaryColor
import presentation.components.AppData
import presentation.contatcts.ContactsScreen

@Composable
fun getGridItems(): List<AppData> {
    val navigator = LocalNavigator.current
    return  listOf(
    AppData(imageRes = Res.drawable.calculator, circleColor = Color.Green, label = "מחשבון", onClick = { /* פעולה */ }),
    AppData(imageRes = Res.drawable.settings, circleColor = Color.Black, label = "הגדרות", onClick = { /* פעולה */ }),
    AppData(imageRes = Res.drawable.camera, circleColor = Color.Gray, label = "מצלמה", onClick = { /* פעולה */ }),
    AppData(imageRes = Res.drawable.email, circleColor = Color(0xFFFFA500), label = "אימייל", onClick = { /* פעולה */ }),
    AppData(imageRes = Res.drawable.store, circleColor = Color.Red, label = "חנות", onClick = { /* פעולה */ }),
    AppData(imageRes = Res.drawable.clock, circleColor = Color(0xFF00008B), label = "שעון", onClick = { /* פעולה */ }),
    AppData(imageRes = Res.drawable.contacts, circleColor = Color.Black, label = "אנשי קשר", onClick = {navigator?.push(ContactsScreen())}),
    AppData(
        imageRes = Res.drawable.white_messages,
        circleColor = Color.Blue,
        label = "הודעות", onClick = { /* פעולה */ }
    ),
    AppData(imageRes = Res.drawable.purse, circleColor = Color(0xFF800080), label = "ארנק", onClick = { /* פעולה */ }),
    AppData(imageRes = Res.drawable.weather, circleColor = Color.Yellow, label = "מזג אוויר", onClick = { /* פעולה */ }),
    AppData(
        imageRes = Res.drawable.documents,
        circleColor = Color.Yellow,
        label = "הקבצים שלי", onClick = { /* פעולה */ }
    ),
    AppData(imageRes = Res.drawable.white_phone, circleColor = primaryColor, label = "טלפון",  onClick = { /* פעולה */ } ),


    )}

