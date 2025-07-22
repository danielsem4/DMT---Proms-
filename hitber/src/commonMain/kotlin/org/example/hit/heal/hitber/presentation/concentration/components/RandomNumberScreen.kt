package org.example.hit.heal.hitber.presentation.concentration.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.primaryColor

// Displays a number that changes color momentarily on click and sends the selected number.
@Composable
fun RandomNumberScreen(
    number: Int?,
    isClickable: Boolean,
    onNumberClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var isClicked by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .background(if (isClicked) primaryColor else Color.White)
            .clickable(enabled = isClickable) {
                isClicked = true
                if (number != null) {
                    onNumberClicked(number)
                }
                coroutineScope.launch {
                    delay(100)
                    isClicked = false
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number?.toString() ?: "",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = if (isClicked) Color.White else primaryColor
        )
    }
}
