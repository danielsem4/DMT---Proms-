package org.example.hit.heal.hitber.presentation.understanding.components

import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.blue_napkin
import dmt_proms.hitber.generated.resources.green_napkin
import dmt_proms.hitber.generated.resources.red_napkin
import dmt_proms.hitber.generated.resources.yellow_napkin
import org.jetbrains.compose.resources.DrawableResource

data class Napkin(
    val image: DrawableResource,
    val xRatio: Float,
    val yRatio: Float,
)

val napkins = listOf(
    Napkin(Res.drawable.red_napkin, 0.1f, 0.18f),
    Napkin(Res.drawable.yellow_napkin, 0.45f, 0.15f),
    Napkin(Res.drawable.green_napkin, 0.3f, 0.2f),
    Napkin(Res.drawable.blue_napkin, 0.65f, 0.17f),
)
