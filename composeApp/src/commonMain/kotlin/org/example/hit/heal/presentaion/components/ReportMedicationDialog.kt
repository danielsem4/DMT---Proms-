package org.example.hit.heal.presentaion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.hit.heal.presentaion.backgroundColor
import org.example.hit.heal.presentaion.primaryColor
import org.jetbrains.compose.resources.painterResource

@Composable
fun ReportMedicationDialog(medicationName: String, onDismiss: () -> Unit) {
    val currentDateTime = remember {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        "${now.dayOfMonth}/${now.monthNumber}/${now.year} ${now.hour}:${now.minute}:${now.second}"
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier

            .border(2.dp, Color.Black, RoundedCornerShape(16.dp)),
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
             //  Icon(
             //      painter = painterResource("images/pill.png"),
             //      contentDescription = null,
             //      modifier = Modifier.size(48.dp)
             //  )
                Text(text = "Report Medication taken", fontWeight = FontWeight.Bold,fontSize = 18.sp,
                    color = Color.Black)
            }
        },
        text = {
            Text(text = "The time I took $medicationName is:\n$currentDateTime",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
                )
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Save", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                }
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Date", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                }
            }
        }
    )


}
