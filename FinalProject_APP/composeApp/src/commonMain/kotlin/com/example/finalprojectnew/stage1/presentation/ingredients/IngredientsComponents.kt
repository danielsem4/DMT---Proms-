package com.example.finalprojectnew.presentation.stage1.ingredients

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// ingredient row with bullet
@Composable
internal fun IngredientRow(text: String) { // inside the box
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text( // name of ingredient
            text = text,
            fontSize = 40.sp,
            textAlign = TextAlign.Right,
            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
            color = Color(0xFF000000)
        )
        Text( // bullet
            text = "•",
            fontSize = 40.sp,
            color = Color(0xFF2E3C9A)
        )
    }
}


@Composable
internal fun ShoppingListCard(title: String, items: List<String>) {
    Box(
        modifier = Modifier
            .width(480.dp)
            .height(606.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, top = 16.dp, end = 32.dp)
        ) {
            Text(
                text = "$title 🛒",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color(0xFF000000)
            )
            items.forEach { IngredientRow(it) }
        }
    }
}