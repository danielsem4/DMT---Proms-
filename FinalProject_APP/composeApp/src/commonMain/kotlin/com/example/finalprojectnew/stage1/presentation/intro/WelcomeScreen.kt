package com.example.finalprojectnew.presentation.stage1.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.presentation.stage1.components.GameButton
import com.example.finalprojectnew.presentation.theme.Colors

@Composable
fun WelcomeScreen(
    onReadInstructions: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Background)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(180.dp))

        Text(
            text = "ברוכים הבאים",
            fontSize = 150.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.PrimaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(0.50f))

        GameButton(
            text = "לחצו לקריאת ההוראות",
            onClick = onReadInstructions,
            modifier = Modifier
                .height(120.dp)
                .padding(horizontal = 48.dp),
            textSize = 60
        )

        Spacer(modifier = Modifier.weight(0.50f))
    }
}