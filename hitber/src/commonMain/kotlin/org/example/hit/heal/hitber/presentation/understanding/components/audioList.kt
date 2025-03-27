package org.example.hit.heal.hitber.presentation.understanding.components

import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.blue_napkin
import dmt_proms.hitber.generated.resources.chicken
import dmt_proms.hitber.generated.resources.coca_cola
import dmt_proms.hitber.generated.resources.grapes
import dmt_proms.hitber.generated.resources.green_napkin
import dmt_proms.hitber.generated.resources.milk
import dmt_proms.hitber.generated.resources.red_napkin
import dmt_proms.hitber.generated.resources.seventh_question_can_to_yellow_napkin_hitbear
import dmt_proms.hitber.generated.resources.seventh_question_chicken_to_green_napkin_hitbear
import dmt_proms.hitber.generated.resources.seventh_question_default_milk_to_red_napkin_hitbear
import dmt_proms.hitber.generated.resources.seventh_question_grapes_to_blue_napkin_hitbear
import dmt_proms.hitber.generated.resources.yellow_napkin
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

//val audioList = listOf(
//    Res.string.seventh_question_default_milk_to_red_napkin_hitbear,
//    Res.string.seventh_question_grapes_to_blue_napkin_hitbear,
//    Res.string.seventh_question_chicken_to_green_napkin_hitbear,
//    Res.string.seventh_question_can_to_yellow_napkin_hitbear
//)


data class AudioItem(
    val audioResId: StringResource,
    val itemResId: DrawableResource,   // משאב תמונה או מזהה של הפריט
    val napkinColorResId: DrawableResource // משאב תמונה או מזהה של צבע המפית
)

val audioList = listOf(
    AudioItem(
        Res.string.seventh_question_default_milk_to_red_napkin_hitbear,
        Res.drawable.milk,
        Res.drawable.red_napkin
    ),
    AudioItem(
        Res.string.seventh_question_grapes_to_blue_napkin_hitbear,
        Res.drawable.grapes,      // תווך המפרט את הפריט (תמונה של ענבים)
        Res.drawable.blue_napkin  // המפית הכחולה
    ),
    AudioItem(
        Res.string.seventh_question_chicken_to_green_napkin_hitbear,
        Res.drawable.chicken,     // תווך המפרט את הפריט (תמונה של עוף)
        Res.drawable.green_napkin // המפית הירוקה
    ),
    AudioItem(
        Res.string.seventh_question_can_to_yellow_napkin_hitbear,
        Res.drawable.coca_cola,         // תווך המפרט את הפריט (תמונה של קופסה)
        Res.drawable.yellow_napkin // המפית הצהובה
    )
)
