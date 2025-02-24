package org.example.hit.heal.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Snowshoeing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.presentaion.BaseScreen

@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {
    BaseScreen(title = "Home") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Messages Section
            MessagesSection {
                Text("Messages", fontSize = 18.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Don't forget to take your test.", fontSize = 14.sp)
                Text("• Take 2 pills at 12:00", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.weight(1f))
            // Feature Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                FeatureButton(icon = Icons.Outlined.Medication, label = "Medications", onClick = {})
                FeatureButton(icon = Icons.Outlined.AutoGraph, label = "Graphs", onClick = {})
                FeatureButton(
                    icon = Icons.Outlined.Snowshoeing,
                    label = "Activities",
                    onClick = {})


            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                FeatureButton(Icons.Outlined.MonitorHeart, label = "Measurements", onClick = {})
            }
        }
    }

}


