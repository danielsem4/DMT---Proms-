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

val staticShapesItem = listOf(
   BuildShapes(Res.drawable.triangle, 0.05f, 0.4f, 0.4f, 0.4f),
    BuildShapes(Res.drawable.rotate_star, 0.5f, -0.25f, 0.5f, 0.5f),
    BuildShapes(Res.drawable.arrow_key, 0.5f, 0.45f, 0.8f, 0.5f),
    BuildShapes(Res.drawable.circle, 0.15f, 0.65f, 0.2f, 0.2f),
    BuildShapes(Res.drawable.black_circle, 0.6f, 0.65f, 0.2f, 0.2f),
    BuildShapes(Res.drawable.vertical_line, 0.22f, 0.3f, 0.45f, 0.5f),
    BuildShapes(Res.drawable.horizontal_line, 0.2f, 0.1f, 0.5f, 0.8f)
)

val draggableShapesItem = listOf(
    BuildShapes(Res.drawable.triangle, 0.8f, 0.05f, 0.8f, 1f),
    BuildShapes(Res.drawable.rotate_star, 0.87f, 0.7f, 0.5f, 0.5f),
    BuildShapes(Res.drawable.arrow_key, 0.7f, 0.1f, 0.8f, 0.5f),
    BuildShapes(Res.drawable.circle, 0.95f, 0.55f, 0.2f, 0.2f),
    BuildShapes(Res.drawable.black_circle, 0.88f, 0.55f, 0.2f, 0.2f),
    BuildShapes(Res.drawable.vertical_line, 0.75f, 0.7f, 0.45f, 0.5f),
    BuildShapes(Res.drawable.horizontal_line, 0.65f, 0.4f, 0.5f, 0.8f)
)