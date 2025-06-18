package org.example.hit.heal.hitber.presentation.understanding.model

import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.blue_napkin
import dmt_proms.hitber.generated.resources.chicken
import dmt_proms.hitber.generated.resources.coca_cola
import dmt_proms.hitber.generated.resources.grapes
import dmt_proms.hitber.generated.resources.green_napkin
import dmt_proms.hitber.generated.resources.milk
import dmt_proms.hitber.generated.resources.red_napkin
import dmt_proms.hitber.generated.resources.yellow_napkin
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
        Res.drawable.milk,
        Res.drawable.red_napkin
    ),
    AudioItem(
        sixthQuestionGrapesToBlueNapkinHitber,
        Res.drawable.grapes,
        Res.drawable.blue_napkin
    ),
    AudioItem(
        sixthQuestionChickenToGreenNapkinHitber,
        Res.drawable.chicken,
        Res.drawable.green_napkin
    ),
    AudioItem(
        sixthQuestionCanToYellowNapkinHitber,
        Res.drawable.coca_cola,
        Res.drawable.yellow_napkin
    )
)
