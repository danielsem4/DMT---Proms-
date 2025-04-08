package org.example.hit.heal.hitber.presentation.buildShape

import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.star
import dmt_proms.hitber.generated.resources.triangle
import org.jetbrains.compose.resources.DrawableResource

data class BuildShapes(
    val image: DrawableResource,
    val xRatio: Float,
    val yRatio: Float,
)

val shapesItem = listOf(
    BuildShapes(Res.drawable.triangle, 0.05f, 0.5f),
    BuildShapes(Res.drawable.star, 0.1f, 0.2f),
)