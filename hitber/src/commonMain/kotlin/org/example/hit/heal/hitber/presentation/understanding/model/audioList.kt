package org.example.hit.heal.hitber.presentation.understanding.model

import org.example.hit.heal.core.presentation.Resources.Icon.blueNapkin
import org.example.hit.heal.core.presentation.Resources.Icon.chicken
import org.example.hit.heal.core.presentation.Resources.Icon.cocaCola
import org.example.hit.heal.core.presentation.Resources.Icon.grapes
import org.example.hit.heal.core.presentation.Resources.Icon.greenNapkin
import org.example.hit.heal.core.presentation.Resources.Icon.milk
import org.example.hit.heal.core.presentation.Resources.Icon.redNapkin
import org.example.hit.heal.core.presentation.Resources.Icon.yellowNapkin
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionCanToYellowNapkinHitber
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionChickenToGreenNapkinHitber
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionDefaultMilkToRedNapkinHitber
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionGrapesToBlueNapkinHitber
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class AudioItem(
    val audioResId: StringResource,
    val itemResId: DrawableResource,
    val napkinColorResId: DrawableResource
)

val audioList = listOf(
    AudioItem(
        sixthQuestionDefaultMilkToRedNapkinHitber,
        milk,
        redNapkin
    ),
    AudioItem(
        sixthQuestionGrapesToBlueNapkinHitber,
        grapes,
        blueNapkin
    ),
    AudioItem(
        sixthQuestionChickenToGreenNapkinHitber,
        chicken,
        greenNapkin
    ),
    AudioItem(
        sixthQuestionCanToYellowNapkinHitber,
        cocaCola,
        yellowNapkin
    )
)
