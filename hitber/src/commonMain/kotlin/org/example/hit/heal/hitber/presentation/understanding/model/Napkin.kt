package org.example.hit.heal.hitber.presentation.understanding.model

import org.example.hit.heal.core.presentation.Resources.Icon.blueNapkin
import org.example.hit.heal.core.presentation.Resources.Icon.greenNapkin
import org.example.hit.heal.core.presentation.Resources.Icon.redNapkin
import org.example.hit.heal.core.presentation.Resources.Icon.yellowNapkin
import org.jetbrains.compose.resources.DrawableResource

data class Napkin(
    val image: DrawableResource,
    val xRatio: Float,
    val yRatio: Float,
)

val napkins = listOf(
    Napkin(redNapkin, 0.1f, 0.08f),
    Napkin(yellowNapkin, 0.45f, 0.05f),
    Napkin(greenNapkin, 0.3f, 0.1f),
    Napkin(blueNapkin, 0.65f, 0.07f),
)
