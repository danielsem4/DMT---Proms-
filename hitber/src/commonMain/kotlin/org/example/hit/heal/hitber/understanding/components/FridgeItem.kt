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
    FridgeItem(Res.drawable.peas, 0.2f, 0.18f),
    FridgeItem(Res.drawable.Cottage, 0.15f, 0.4f),
    FridgeItem(Res.drawable.yogurt, 0.35f, 0.4f),
    FridgeItem(Res.drawable.grapes, 0.15f, 0.52f),
    FridgeItem(Res.drawable.coca_cola, 0.35f, 0.52f),
    FridgeItem(Res.drawable.chicken, 0.25f, 0.65f),
    FridgeItem(Res.drawable.orange_juice, 0.7f, 0.47f),
    FridgeItem(Res.drawable.milk, 0.6f, 0.6f)
    )


