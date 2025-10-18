// The conveyor game -> only UI screen
package com.example.finalprojectnew.presentation.stage1.conveyor_game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.presentation.stage1.components.RecipeImageCompose

@Composable
fun ConveyorGameScreen(
    recipeId: String,

    onFinish: (String) -> Unit
) {
    // Create a VM for this recipe and collect its live state.
    val vm = remember(recipeId) { ConveyorGameViewModel(recipeId) }
    val state by vm.state.collectAsState()

    // When the game finish, move to the next screen
    LaunchedEffect(state.finished) {
        if (state.finished) onFinish(recipeId)
    }

    // When it finish, stop background work (timers, loops).
    DisposableEffect(Unit) {
        onDispose { vm.clear() }
    }

    // Screen layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFAF1))
            .padding(8.dp)
    ) {
        // TIMER
        Text(
            text = "🕒 נותרו: ${state.timeLeft} שניות",
            fontSize = 50.sp,
            color = Color.Red,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(8.dp))

        // TOP AREA SCREEN: recipe image + ingredients checklist
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f), // ~60% of height
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Recipe image (pie / salad / cake)
            RecipeImageCompose(
                recipeName = recipeId,
                modifier = Modifier
                    .width(280.dp)
                    .height(280.dp)
            )

            // Checklist
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp)
            ) {
                Text(
                    text = ":מצרכים",
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E3C9A),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Right
                )

                // We pass the ingredient names and a mutable state list of checked flags
                IngredientsChecklist(
                    items = state.ingredients,
                    checked = state.checked.toMutableStateList()
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // BOTTOM AREA SCREEN: the conveyor
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // ~40% of height
                .background(Color.White)
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Text(
                text = ":לבחירת המוצר המתאים יש ללחוץ עליו",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E3C9A),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                textAlign = TextAlign.Center
            )

            // background + moving items above it
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                ConveyorBackground(Modifier.fillMaxSize())

                // Draw each moving item at its X position based on `offset + index * stride`
                state.movingItems.forEachIndexed { index, item ->
                    val x = state.offset + index * state.stride - 120f
                    // only show items in the screen area
                    if (x in -180f..1800f) {

                        // Sets selection mode -
                        // (Not selected / correctly selected / incorrectly selected)
                        // This will affect the color/frame of the ProductChip
                        val isSelected = item.name in state.selectedNames
                        val selection = when {
                            !isSelected    -> SelectionState.Unselected
                            item.isCorrect -> SelectionState.Correct
                            else           -> SelectionState.Wrong
                        }


                        ProductChip(
                            name = item.name,
                            selection = selection,
                            modifier = Modifier
                                .offset(x.dp, 12.dp) // place item bubbles
                                .clickable(enabled = !isSelected && !state.finished) {
                                    // Report the click to the ViewModel
                                    vm.onProductClicked(item)
                                }
                        )
                    }
                }
            }
        }
    }
}
