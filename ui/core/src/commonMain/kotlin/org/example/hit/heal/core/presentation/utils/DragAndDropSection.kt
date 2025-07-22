package org.example.hit.heal.core.presentation.utils

import androidx.compose.ui.geometry.Offset

/**
 * Determines if a draggable object, which can be either circular or rectangular, is entirely within a specified target area.
 * Takes into account the size and position of both objects, as well as an optional threshold margin around the target.
 */

fun isObjectInsideTargetArea(
    targetPosition: Offset,
    draggablePosition: Offset,
    targetSize: Pair<Float, Float>,
    draggableSize: Pair<Float, Float> = 0f to 0f,
    isCircle: Boolean,
    threshold: Float = 0f
): Boolean {
    val (targetWidth, targetHeight) = targetSize
    val (draggableWidth, draggableHeight) = draggableSize

    val targetLeft = targetPosition.x - threshold
    val targetTop = targetPosition.y - threshold
    val targetRight = targetPosition.x + targetWidth + threshold
    val targetBottom = targetPosition.y + targetHeight + threshold

    val draggableLeft: Float
    val draggableTop: Float
    val draggableRight: Float
    val draggableBottom: Float

    if (isCircle) {
        val draggableRadius = draggableWidth / 2
        draggableLeft = draggablePosition.x - draggableRadius
        draggableTop = draggablePosition.y - draggableRadius
        draggableRight = draggablePosition.x + draggableRadius
        draggableBottom = draggablePosition.y + draggableRadius
    } else {
        draggableLeft = draggablePosition.x
        draggableTop = draggablePosition.y
        draggableRight = draggableLeft + draggableWidth
        draggableBottom = draggableTop + draggableHeight
    }

    val isInside = draggableLeft >= targetLeft && draggableRight <= targetRight &&
            draggableTop >= targetTop && draggableBottom <= targetBottom

    return isInside
}
