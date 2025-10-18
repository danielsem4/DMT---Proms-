package com.example.finalprojectnew.presentation.stage1.ingredients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.di.ServiceLocator
import com.example.finalprojectnew.presentation.stage1.components.GameButton
import com.example.finalprojectnew.presentation.stage1.components.RecipeImageCompose

@Composable
fun IngredientsScreen(
    recipeId: String,
    onStartShopping: (String) -> Unit,
) {
    val recipe = remember(recipeId) { ServiceLocator.getRecipeById(recipeId) }
        ?: error("Recipe $recipeId not found")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFAF1))
            .padding(top = 32.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "מצרכים להכנת ${recipe.name}",
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E3C9A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .width(340.dp)
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RecipeImageCompose(
                        recipeName = recipe.key,
                        modifier = Modifier
                            .width(340.dp)
                            .height(460.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    GameButton(
                        text = "לחץ להתחלת קנייה",
                        onClick = { onStartShopping(recipe.id) },
                        modifier = Modifier
                            .width(320.dp)
                            .height(140.dp),
                        backgroundColor = Color(0xFF2E3C9A)
                    )
                }

                ShoppingListCard(
                    title = "סל קניות",
                    items = recipe.ingredients.map { it.name }
                )
            }

        }
    }
}