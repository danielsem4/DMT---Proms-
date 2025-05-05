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
    return listOf(
        AppData(imageRes = Res.drawable.calculator, circleColor = Color.Green, label = "מחשבון"),
        AppData(imageRes = Res.drawable.settings, circleColor = Color.Black, label = "הגדרות"),
        AppData(imageRes = Res.drawable.camera, circleColor = Color.Gray, label = "מצלמה"),
        AppData(imageRes = Res.drawable.email, circleColor = Color(0xFFFFA500), label = "אימייל"),
        AppData(imageRes = Res.drawable.store, circleColor = Color.Red, label = "חנות"),
        AppData(imageRes = Res.drawable.clock, circleColor = Color(0xFF00008B), label = "שעון"),
        AppData(imageRes = Res.drawable.contacts, circleColor = Color.Black, label = "אנשי קשר"),
        AppData(imageRes = Res.drawable.white_messages, circleColor = Color.Blue, label = "הודעות"),
        AppData(imageRes = Res.drawable.purse, circleColor = Color(0xFF800080), label = "ארנק"),
        AppData(imageRes = Res.drawable.weather, circleColor = Color.Yellow, label = "מזג אוויר"),
        AppData(imageRes = Res.drawable.documents, circleColor = Color.Yellow, label = "הקבצים שלי"),
        AppData(imageRes = Res.drawable.white_phone, circleColor = primaryColor, label = "טלפון"))
}

