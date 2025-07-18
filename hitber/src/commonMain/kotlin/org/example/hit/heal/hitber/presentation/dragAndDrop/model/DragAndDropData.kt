package org.example.hit.heal.hitber.presentation.dragAndDrop.model

import androidx.compose.ui.graphics.Color
import org.example.hit.heal.core.presentation.Resources.String.seventhQuestionHitberBlackCircle
import org.example.hit.heal.core.presentation.Resources.String.seventhQuestionHitberBlueCircle
import org.example.hit.heal.core.presentation.Resources.String.seventhQuestionHitberGreenCircle
import org.example.hit.heal.core.presentation.Resources.String.seventhQuestionHitberYellowCircle

// Instruction labels paired with their colors for the drag-and-drop task.
// Used randomly to assign colors during the task.

val instructions = listOf(
    seventhQuestionHitberGreenCircle to Color.Green,
    seventhQuestionHitberBlackCircle to Color.Black,
    seventhQuestionHitberBlueCircle to Color.Blue,
    seventhQuestionHitberYellowCircle to Color.Yellow
)

// Colors of the circles used in the drag-and-drop interaction
val circleColors = listOf(Color.Black, Color.Green, Color.Blue, Color.Yellow)
