package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Colors
import org.example.hit.heal.core.presentation.Colors.primaryColor

@Composable
fun OnOffToggle() {
    var isOn by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (isOn) Colors.primaryColor else Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                .clickable { isOn = true }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "On",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isOn) Color.White else primaryColor
            )
        }

        Spacer(Modifier.weight(.1f))

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (isOn) Color.White else primaryColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                .clickable { isOn = false }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Off",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isOn) primaryColor else Color.White
            )
        }
    }
}