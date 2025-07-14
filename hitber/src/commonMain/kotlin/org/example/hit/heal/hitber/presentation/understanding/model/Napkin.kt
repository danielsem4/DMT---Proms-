package org.example.hit.heal.hitber.presentation.understanding.model

import androidx.compose.ui.graphics.Color
import org.example.hit.heal.core.presentation.Resources.Icon.napkinIcon
import org.jetbrains.compose.resources.DrawableResource

data class Napkin(
    val image: DrawableResource = napkinIcon,
    val xRatio: Float,
    val yRatio: Float,
    val tint: Color
)

val napkins = listOf(
    Napkin(xRatio = 0.2f,yRatio = 0.08f, tint =  Color.Red),
    Napkin(xRatio = 0.4f, yRatio = 0.1f, tint = Color.Green),
    Napkin(xRatio = 0.55f, yRatio = 0.05f, tint = Color.Yellow),
    Napkin(xRatio = 0.75f, yRatio = 0.07f, tint= Color.Blue),
)
