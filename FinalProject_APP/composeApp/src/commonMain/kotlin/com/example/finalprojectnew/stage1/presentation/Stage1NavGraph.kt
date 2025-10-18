package com.example.finalprojectnew.stage1.presentation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.finalprojectnew.presentation.stage1.conveyor_game.ConveyorGameScreen
import com.example.finalprojectnew.presentation.stage1.finish.FinishScreen
import com.example.finalprojectnew.presentation.stage1.ingredients.IngredientsScreen
import com.example.finalprojectnew.presentation.stage1.intro.IntroScreen
import com.example.finalprojectnew.presentation.stage1.recipe_selection.RecipeSelectionScreen
import com.example.finalprojectnew.presentation.stage1.welcome.WelcomeScreen

enum class Stage1Screen { Welcome, Intro, RecipeSelection, Ingredients, Conveyor, Finish }


@Composable
fun Stage1NavGraph(
    onStage1Done: () -> Unit
) {
    var current by rememberSaveable { mutableStateOf(Stage1Screen.Welcome) } // first screen - welcome
    var recipeId by rememberSaveable { mutableStateOf<String?>(null) }

    when (current) {
        Stage1Screen.Welcome -> WelcomeScreen(
            onReadInstructions = { current = Stage1Screen.Intro }
        )

        Stage1Screen.Intro -> IntroScreen(
            onStartGame = { current = Stage1Screen.RecipeSelection }
        )

        Stage1Screen.RecipeSelection -> RecipeSelectionScreen(
            onRecipeChosen = { id ->
                recipeId = id
                current = Stage1Screen.Ingredients
            }
        )

        Stage1Screen.Ingredients -> {
            val id = recipeId
            if (id == null) {
                current = Stage1Screen.RecipeSelection // if we dont have recipeID for some reason
            } else {
                IngredientsScreen(
                    recipeId = id,
                    onStartShopping = { chosenId ->
                        recipeId = chosenId
                        current = Stage1Screen.Conveyor
                    }
                )
            }
        }

        Stage1Screen.Conveyor -> {
            val id = recipeId
            if (id == null) {
                current = Stage1Screen.RecipeSelection
            } else {
                ConveyorGameScreen(
                    recipeId = id,
                    onFinish = { current = Stage1Screen.Finish }
                )
            }
        }

        Stage1Screen.Finish -> {
            FinishScreen(
                onNextStage = { onStage1Done() }
            )
        }
    }
}
