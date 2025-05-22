package org.example.hit.heal.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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
import org.example.hit.heal.core.presentation.BaseScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {
    val homeViewModel: HomeViewModel = koinViewModel()

    BaseScreen(
        title = "Home",
        navigationIcon = {
            IconButton(
                onClick = {
                    homeViewModel.logout()
                    onLogout()
                }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout",
                    tint = Color.White
                )
            }
        }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Using the updated MessagesSection with a distinct header style
            MessagesSection {
                // Content below the header inside the MessagesSection
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Don't forget to take your test.", fontSize = 14.sp, color = Color.Black)
                Text("• Take 2 pills at 12:00", fontSize = 14.sp, color = Color.Black)
            }

            // Push the feature buttons to the bottom of the screen
            Spacer(modifier = Modifier.weight(1f))

            // First row: three feature buttons evenly spaced
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FeatureButton(icon = Icons.Outlined.Medication, label = "Medications", onClick = {})
                FeatureButton(icon = Icons.Outlined.AutoGraph, label = "Graphs", onClick = {})
                FeatureButton(icon = Icons.Outlined.Snowshoeing, label = "Activities", onClick = {})
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Second row: a single feature button aligned to the start
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                FeatureButton(icon = Icons.Outlined.MonitorHeart, label = "Measurements", onClick = {})
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}