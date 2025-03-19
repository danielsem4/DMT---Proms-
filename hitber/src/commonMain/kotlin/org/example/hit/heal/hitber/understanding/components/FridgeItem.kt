package org.example.hit.heal.hitber.understanding.components

import dmt_proms.hitber.generated.resources.Cottage
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.chicken
import dmt_proms.hitber.generated.resources.coca_cola
import dmt_proms.hitber.generated.resources.grapes
import dmt_proms.hitber.generated.resources.milk
import dmt_proms.hitber.generated.resources.orange_juice
import dmt_proms.hitber.generated.resources.peas
import dmt_proms.hitber.generated.resources.yogurt
import org.jetbrains.compose.resources.DrawableResource

data class FridgeItem(
    val image: DrawableResource,
    val xRatio: Float,
    val yRatio: Float,
)

val fridgeItems = listOf(
    FridgeItem(Res.drawable.peas, 0.07f, 0.055f),
    FridgeItem(Res.drawable.milk, 0.19f, 0.22f),
    FridgeItem(Res.drawable.Cottage, 0.07f, 0.14f),
    FridgeItem(Res.drawable.yogurt, 0.12f, 0.14f),
    FridgeItem(Res.drawable.grapes, 0.07f, 0.18f),
    FridgeItem(Res.drawable.coca_cola, 0.12f, 0.18f),
    FridgeItem(Res.drawable.chicken, 0.1f, 0.25f),
    FridgeItem(Res.drawable.orange_juice, 0.21f, 0.15f)


)


