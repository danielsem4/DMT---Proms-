package org.example.hit.heal.hitber.presentation.understanding.model

import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.blue_napkin
import dmt_proms.hitber.generated.resources.chicken
import dmt_proms.hitber.generated.resources.coca_cola
import dmt_proms.hitber.generated.resources.grapes
import dmt_proms.hitber.generated.resources.green_napkin
import dmt_proms.hitber.generated.resources.milk
import dmt_proms.hitber.generated.resources.red_napkin
import dmt_proms.hitber.generated.resources.sixth_question_can_to_yellow_napkin_hitbear
import dmt_proms.hitber.generated.resources.sixth_question_chicken_to_green_napkin_hitbear
import dmt_proms.hitber.generated.resources.sixth_question_default_milk_to_red_napkin_hitbear
import dmt_proms.hitber.generated.resources.sixth_question_grapes_to_blue_napkin_hitbear
import dmt_proms.hitber.generated.resources.yellow_napkin
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class AudioItem(
    val audioResId: StringResource,
    val itemResId: DrawableResource,
    val napkinColorResId: DrawableResource
)

val audioList = listOf(
    AudioItem(
        Res.string.sixth_question_default_milk_to_red_napkin_hitbear,
        Res.drawable.milk,
        Res.drawable.red_napkin
    ),
    AudioItem(
        Res.string.sixth_question_grapes_to_blue_napkin_hitbear,
        Res.drawable.grapes,
        Res.drawable.blue_napkin
    ),
    AudioItem(
        Res.string.sixth_question_chicken_to_green_napkin_hitbear,
        Res.drawable.chicken,
        Res.drawable.green_napkin
    ),
    AudioItem(
        Res.string.sixth_question_can_to_yellow_napkin_hitbear,
        Res.drawable.coca_cola,
        Res.drawable.yellow_napkin
    )
)
