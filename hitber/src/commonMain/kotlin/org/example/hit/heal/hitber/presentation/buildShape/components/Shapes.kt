package org.example.hit.heal.hitber.presentation.buildShape.components

import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.arrow_key
import dmt_proms.hitber.generated.resources.black_circle
import dmt_proms.hitber.generated.resources.circle
import dmt_proms.hitber.generated.resources.horizontal_line
import dmt_proms.hitber.generated.resources.rotate_star
import dmt_proms.hitber.generated.resources.triangle
import dmt_proms.hitber.generated.resources.vertical_line
import org.jetbrains.compose.resources.DrawableResource

data class BuildShapes(
    val image: DrawableResource,
    val xRatio: Float,
    val yRatio: Float,
    val width: Float,
    val height: Float
)

val shapesItem = listOf(
    BuildShapes(Res.drawable.triangle, 0.05f, 0.4f, 0.4f, 0.4f),
    BuildShapes(Res.drawable.rotate_star, 0.15f, 0.28f, 0.25f, 0.25f),
    BuildShapes(Res.drawable.arrow_key, 0.15f, 0.54f, 0.4f, 0.25f),
    BuildShapes(Res.drawable.circle, 0.08f, 0.64f, 0.1f, 0.1f),
    BuildShapes(Res.drawable.black_circle, 0.17f, 0.64f, 0.1f, 0.1f),
    BuildShapes(Res.drawable.vertical_line, 0.09f, 0.47f, 0.25f, 0.3f),
    BuildShapes(Res.drawable.horizontal_line, 0.086f, 0.44f, 0.25f, 0.33f)

)