// finish game screen
package com.example.finalprojectnew.presentation.stage1.finish

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FinishScreen(
    onNextStage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFAF1))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "שלב 1 הסתיים",
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E3C9A),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(48.dp))

        Button(
            onClick = onNextStage,
            modifier = Modifier
                .padding(horizontal = 48.dp)
                .height(100.dp)
                .fillMaxWidth(0.6f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E3C9A)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "לחצו למעבר לשלב הבא",
                fontSize = 42.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}