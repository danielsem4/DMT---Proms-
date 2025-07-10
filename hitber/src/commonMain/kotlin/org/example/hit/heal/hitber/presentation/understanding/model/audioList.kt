package org.example.hit.heal.hitber.presentation.understanding.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.Resources.Icon.chicken
import org.example.hit.heal.core.presentation.Resources.Icon.cocaCola
import org.example.hit.heal.core.presentation.Resources.Icon.grapes
import org.example.hit.heal.core.presentation.Resources.Icon.milk
import org.example.hit.heal.core.presentation.Resources.Icon.napkinIcon
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionCanToYellowNapkinHitber
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionChickenToGreenNapkinHitber
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionDefaultMilkToRedNapkinHitber
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionGrapesToBlueNapkinHitber
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource


data class AudioItem(
    val audioResId: StringResource,
    val itemResId: DrawableResource,
    val napkinTint: Color
)

val napkinBaseIcon = napkinIcon

val audioList = listOf(
    AudioItem(
        sixthQuestionDefaultMilkToRedNapkinHitber,
        milk,
        Red
    ),
    AudioItem(
        sixthQuestionGrapesToBlueNapkinHitber,
        grapes,
        Blue
    ),
    AudioItem(
        sixthQuestionChickenToGreenNapkinHitber,
        chicken,
        Green
    ),
    AudioItem(
        sixthQuestionCanToYellowNapkinHitber,
        cocaCola,
        Yellow
    )
)