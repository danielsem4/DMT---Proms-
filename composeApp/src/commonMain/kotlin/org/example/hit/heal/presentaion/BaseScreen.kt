package org.example.hit.heal.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BaseScreen(
    title: String,
    onPrevClick: (() -> Unit)? = null,
    onNextClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryColor)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.h5
            )
        }

        // Dynamic Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }

        // Bottom Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            onPrevClick?.let {
                Button(
                    onClick = it, colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Previous", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            onNextClick?.let {
                Button(
                    onClick = it, colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Next", color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
fun SampleScreen() {
    BaseScreen(
        title = "Sample",
        onPrevClick = { /* Handle previous */ },
        onNextClick = { /* Handle next */ }
    ) {
        Text(text = "How do you feel?")
        TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
    }
}
