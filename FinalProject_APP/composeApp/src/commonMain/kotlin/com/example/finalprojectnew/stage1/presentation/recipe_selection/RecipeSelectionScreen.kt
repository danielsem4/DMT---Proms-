package com.example.finalprojectnew.presentation.stage1.recipe_selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.presentation.stage1.components.RecipeButton
import com.example.finalprojectnew.presentation.stage1.components.RecipeImageCompose

@Composable
fun RecipeSelectionScreen(
    onRecipeChosen: (String) -> Unit
) {
    val imageSize = 360.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFAF1))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "מתכונים לבישול ואפייה",
            fontSize = 80.sp,
            color = Color(0xFF2E3C9A),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "יש לבחור במנה להכנה על ידי לחיצה על שם המנה",
            fontSize = 45.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            RecipeButton("פשטידה") { onRecipeChosen("pie") }
            Spacer(modifier = Modifier.width(190.dp))
            RecipeButton("סלט") { onRecipeChosen("salad") }
            Spacer(modifier = Modifier.width(190.dp))
            RecipeButton("עוגה") { onRecipeChosen("cake") }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            RecipeImageCompose("pie",   modifier = Modifier.size(imageSize))
            RecipeImageCompose("salad", modifier = Modifier.size(imageSize))
            RecipeImageCompose("cake",  modifier = Modifier.size(imageSize))
        }
    }
}