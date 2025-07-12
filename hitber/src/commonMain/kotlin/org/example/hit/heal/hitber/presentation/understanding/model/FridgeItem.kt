package org.example.hit.heal.hitber.presentation.understanding.model

import org.example.hit.heal.core.presentation.Resources.Icon.chicken
import org.example.hit.heal.core.presentation.Resources.Icon.cocaCola
import org.example.hit.heal.core.presentation.Resources.Icon.cottage
import org.example.hit.heal.core.presentation.Resources.Icon.grapes
import org.example.hit.heal.core.presentation.Resources.Icon.milk
import org.example.hit.heal.core.presentation.Resources.Icon.orangeJuice
import org.example.hit.heal.core.presentation.Resources.Icon.peas
import org.example.hit.heal.core.presentation.Resources.Icon.yogurt
import org.jetbrains.compose.resources.DrawableResource

data class FridgeItem(
    val image: DrawableResource,
    val xRatio: Float,
    val yRatio: Float,
)

val fridgeItems = listOf(
    FridgeItem(peas, 0.2f, 0.18f),
    FridgeItem(cottage, 0.15f, 0.4f),
    FridgeItem(yogurt, 0.35f, 0.4f),
    FridgeItem(grapes, 0.15f, 0.52f),
    FridgeItem(cocaCola, 0.35f, 0.52f),
    FridgeItem(chicken, 0.25f, 0.65f),
    FridgeItem(orangeJuice, 0.7f, 0.47f),
    FridgeItem(milk, 0.6f, 0.6f)
    )


